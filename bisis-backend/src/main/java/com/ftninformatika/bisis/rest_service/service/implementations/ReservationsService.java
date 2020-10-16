package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.Reservation;
import com.ftninformatika.bisis.reservations.Reservations;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.service.interfaces.ReservationsServiceInterface;
import com.ftninformatika.util.constants.ReservationsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReservationsService implements ReservationsServiceInterface {
    @Autowired
    LibraryMemberRepository libraryMemberRepository;

    @Autowired
    SublocationRepository sublocationRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    ItemAvailabilityRepository itemAvailabilityRepository;

    @Autowired
    ItemStatusRepository itemStatusRepository;

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Object reserveBook(String authToken, String library, String mongoRecordId, String coderId) {
        String userId;
        boolean isBgb = false;

        LibraryMember libraryMember = libraryMemberRepository.findByAuthToken(authToken);
        if (libraryMember == null || libraryMember.getIndex() == null) // index je u stvari mongo ID za membera.
            return null;

        // get user ID for reservation
        Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
        if (member.isPresent()) {
            userId = member.get().getUserId();
        } else {
            return null;
        }

        // todo smestiti negde u config
        if (library.equals("bgb")) {
            isBgb = true;
            Sublocation sublocation = sublocationRepository.getByCoder_Id(coderId, library);
        } else {
            Location location = locationRepository.getByCoder_Id(coderId, library);
        }

        // na frontu je recordId u stvari mongo id
        Optional<Record> record = recordsRepository.findById(mongoRecordId);
        if (record.isPresent()) {
            List<Primerak> primerci = record.get().getPrimerci();

            if (primerci != null && primerci.size() > 0) {
                // filter books
                HashMap<String, Integer> filteredBooks = filterBooksByLocationAndBorrowedBooks(primerci, library, coderId, isBgb);

                // all books are available => no reservation
                if (filteredBooks.size() == 0) {
                    return ReservationsConstants.NORESERVATION;
                }
                // this book has never been reserved before
                else if (filteredBooks.containsValue(1)) {
                    for (Map.Entry entry : filteredBooks.entrySet()) {
                        if (entry.getValue().equals(1)) {
                            Reservations reservations = new Reservations();
                            reservations.setCtlgNo((String) entry.getKey());
                            return createNewReservation(reservations, userId);
                        }
                    }
                }
                // no one has reserved this book
                else if (filteredBooks.containsValue(2)) {
                    for (Map.Entry entry : filteredBooks.entrySet()) {
                        if (entry.getValue().equals(2)) {
                            Reservations reservations = reservationsRepository.findByCtlgNo((String) entry.getKey());
                            return createNewReservation(reservations, userId);
                        }
                    }
                }
                // all books have at least one person waiting in line and choosing the most optimal one
                else {
                    Reservations reservations = reservationsRepository.getReservationsByMaxReservationsSize(filteredBooks.keySet());
                    return createNewReservation(reservations, userId);
                }
            }
        }
        return null;
    }

    /**
     * This method filters books.
     *
     * @param primerci list of all books
     * @param library the library in which the logged in user is a member
     * @param coderId the library location ID
     * @param isBgb boolean indicates whether the reservation process is intended for bgb libraries
     * @return list of filtered books
     */
    private HashMap<String, Integer> filterBooksByLocationAndBorrowedBooks(List<Primerak> primerci, String library, String coderId, boolean isBgb) {
        // Sifrarnici ItemStatus-a za trenutnu bibl
        Map<String, ItemStatus> itemStatusMap = itemStatusRepository.getCoders(library).stream().collect(Collectors.toMap(ItemStatus::getCoder_id, sl -> sl));
        HashMap<String, Integer> filteredBooks = new HashMap<>();

        // za bgb se uzima podlok, za sve druge odeljenje.
        for (Primerak p : primerci) {
            boolean sameLocation = false;
            if (isBgb) {
                if (p.getSigPodlokacija().equals(coderId)) {
                    sameLocation = true;
                }
            } else {
                if (p.getOdeljenje().equals(coderId)) {
                    sameLocation = true;
                }
            }
            if (sameLocation) {
                // Dobavljanje IS za odredjenu sifru statusa u datoj bibl
                ItemStatus is = itemStatusMap.get(p.getStatus());
                if (is != null && is.isLendable() && is.isShowable()) {
                    ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(p.getInvBroj());
                    if (ia.getBorrowed()) {
                        Reservations reservations = reservationsRepository.findByCtlgNo(p.getInvBroj());

                        // postoji primerak za koji jos nisu bile kreirane rezervacije
                        if (reservations == null) {
                            filteredBooks.put(p.getInvBroj(), 1);
                            return filteredBooks;
                        } else if (reservations.getReservations().size() == 0) {
                            // niko ne ceka u redu
                            filteredBooks.put(p.getInvBroj(), 2);
                            return filteredBooks;
                        } else {
                            filteredBooks.put(p.getInvBroj(), 0);
                        }
                    }
                }
            }
        }
        return filteredBooks;
    }

    // todo za razmisliti da li u IA da se cuva lista
    // todo u member lista rez ako treba istorija

    /**
     * This method creates a new reservation for a given user, adds it to the reservations queue and saves it in db.
     *
     * @param reservations object that contains reservations queue
     * @param userId user ID
     * @return created reservation
     */
    private Reservation createNewReservation(Reservations reservations, String userId) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setUserId(userId);

        reservations.getReservations().add(reservation);
        reservationsRepository.save(reservations);

        // todo ubaciti rezervaciju u listu rezervacija kod membera
        return reservation;
    }
}
