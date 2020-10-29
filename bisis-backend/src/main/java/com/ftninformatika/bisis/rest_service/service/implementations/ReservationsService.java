package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.Reservation;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.exceptions.ReservationNotAllowedException;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.service.interfaces.ReservationsServiceInterface;
import com.ftninformatika.util.constants.ReservationsConstants;
import org.bson.types.ObjectId;
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
    MemberRepository memberRepository;

    @Autowired
    OpacSearchService opacSearchService;

    @Override
    public Object reserveBook(String authToken, String library, String record_id, String coderId) {
        boolean isBgb = false;

        LibraryMember libraryMember = libraryMemberRepository.findByAuthToken(authToken);
        if (libraryMember == null || libraryMember.getIndex() == null) // index je u stvari mongo ID za membera.
            return ReservationsConstants.UNKNOWNMEMBER;

        Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
        if (!member.isPresent()) {
            return ReservationsConstants.UNKNOWNMEMBER;
        }

        // check if user already has 3 reservations that are not picked up yet
        long numberOfCurrentReservations = member.get().getReservations().stream()
                .filter(reservation -> !reservation.isBookPickedUp())
                .count();

        if (numberOfCurrentReservations >= 3) {
            return ReservationsConstants.LIMITEXCEEDED;
        }

        // todo smestiti negde u config
        if (library.equals("bgb") || library.equals("bmb")) {
            isBgb = true;
            Sublocation sublocation = sublocationRepository.getByCoder_Id(coderId, library);
        } else {
            Location location = locationRepository.getByCoder_Id(coderId, library);
        }

        Optional<Record> record = recordsRepository.findById(record_id);

        if (record.isPresent()) {
            List<Primerak> primerci = record.get().getPrimerci();

            if (primerci != null && primerci.size() > 0) {
                // filter books
                List<Primerak> filteredBooks = filterBooksByLocationAndBorrowedBooks(primerci, library, coderId, isBgb);

                // all books are available => no reservation
                if (filteredBooks.size() == 0) {
                    return ReservationsConstants.NORESERVATION;
                } else {
                    return createNewReservation(member.get(), record.get(), coderId);
                }

            }
        }
        return null;
    }

    @Override
    public List<ReservationDTO> getReservationsByUser(String library, String authToken) {
        LibraryMember libraryMember = libraryMemberRepository.findByAuthToken(authToken);
        if (libraryMember == null || libraryMember.getIndex() == null)
            return null;

        Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
        if (!member.isPresent()) {
            return null;
        }

        List<ReservationOnProfile> reservations = member.get().getReservations();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();

        for (ReservationOnProfile reservation : reservations) {
            if (!reservation.isBookPickedUp()) {                        // get only active reservations
                Optional<Record> record = recordsRepository.findById(reservation.getRecord_id());
                if (record.isPresent()) {
                    // get the name of a branch of a particular library
                    String locationDescription = "";
                    if (library.equals("bgb") || library.equals("bmb")) {
                        Sublocation sublocation = sublocationRepository.getByCoder_Id(reservation.getCoderId(), library);
                        locationDescription = sublocation.getDescription();
                    } else {
                        Location location = locationRepository.getByCoder_Id(reservation.getCoderId(), library);
                        locationDescription = location.getDescription();
                    }

                    // get a book for a given record to extract title and authors
                    Book book = opacSearchService.getBookByRec(record.get());

                    ReservationDTO reservationDTO = ReservationDTO.convertToDto(reservation, book, locationDescription);
                    reservationDTOS.add(reservationDTO);
                }
            }
        }
        return reservationDTOS;
    }

    @Override
    public Boolean deleteReservation(String authToken, String reservationId) {
        LibraryMember libraryMember = libraryMemberRepository.findByAuthToken(authToken);
        Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
        if (!member.isPresent()) {
            return false;
        }

        String record_id = "";
        List<ReservationOnProfile> activeReservations = member.get().getReservations();

        // delete the reservation from the user's reservations list
        Iterator<ReservationOnProfile> iter = activeReservations.iterator();

        while (iter.hasNext()) {
            ReservationOnProfile reservationOnProfile = iter.next();
            if (reservationOnProfile.get_id().equals(reservationId)) {
                record_id = reservationOnProfile.getRecord_id();
                iter.remove();
                memberRepository.save(member.get());
            }
        }

        // delete the reservation from the queue
        Optional<Record> record = recordsRepository.findById(record_id);
        if (record.isPresent()) {
            LinkedList<ReservationInQueue> reservations = record.get().getReservations();
            reservations.removeIf(pr -> pr.getUserId().equals(member.get().getUserId()));
            recordsRepository.save(record.get());
            return true;
        }
        return false;
    }

    /**
     * Filters books by location and status = borrowed
     *
     * @param primerci The list of all books
     * @param library  The library in which the logged user is a member
     * @param coderId  The library location ID
     * @param isBgb    The boolean indicates whether the reservation process is intended for bgb libraries
     * @return The list of filtered books
     */
    private List<Primerak> filterBooksByLocationAndBorrowedBooks(List<Primerak> primerci, String library,
                                                                 String coderId, boolean isBgb) {
        // Sifrarnici ItemStatus-a za trenutnu bibl
        Map<String, ItemStatus> itemStatusMap = itemStatusRepository.getCoders(library).stream()
                .collect(Collectors.toMap(ItemStatus::getCoder_id, sl -> sl));

        List<Primerak> filteredBooks = new ArrayList<>();

        for (Primerak p : primerci) {
            // check if location is the same
            boolean sameLocation = false;
            if (isBgb) {                       // todo: i bmb (citanje iz konfiguracije biblioteke)
                if (p.getSigPodlokacija().equals(coderId)) {
                    sameLocation = true;
                }
            } else {
                if (p.getOdeljenje().equals(coderId)) {
                    sameLocation = true;
                }
            }
            if (sameLocation) {
                // check if the book is already borrowed
                ItemStatus is = itemStatusMap.get(p.getStatus());
                if (is != null && is.isLendable() && is.isShowable()) {
                    ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(p.getInvBroj());
                    if (ia.getBorrowed()) {
                        filteredBooks.add(p);
                    }
                }
            }
        }
        return filteredBooks;
    }

    /**
     * Creates a new reservation for the given record and saves it to the record's reservations queue.
     *
     * @param member The member that is creating reservation
     * @param record The record in which the reservation will be saved
     * @return The created reservation
     */
    private Reservation createNewReservation(Member member, Record record, String coderId) {
        ReservationInQueue reservationInQueue = new ReservationInQueue();
        reservationInQueue.set_id(String.valueOf(new ObjectId()));
        reservationInQueue.setReservationDate(new Date());
        reservationInQueue.setUserId(member.getUserId());
        reservationInQueue.setCoderId(coderId);

        record.appendReservation(reservationInQueue);
        recordsRepository.save(record);

        ReservationOnProfile reservationOnProfile = new ReservationOnProfile();
        reservationOnProfile.set_id(String.valueOf(new ObjectId()));
        reservationOnProfile.setReservationDate(reservationInQueue.getReservationDate());
        reservationOnProfile.setRecord_id(record.get_id());
        reservationOnProfile.setCoderId(coderId);
        reservationOnProfile.setReservationStatus(ReservationStatus.WAITING_IN_QUEUE);

        member.appendReservation(reservationOnProfile);
        memberRepository.save(member);

        return reservationInQueue;
    }


}
