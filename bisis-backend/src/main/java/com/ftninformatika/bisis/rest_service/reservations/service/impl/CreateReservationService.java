package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.Reservation;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.CircConfigRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.CreateReservationServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import com.ftninformatika.util.constants.ReservationsConstants;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author marijakovacevic
 */

@Service
public class CreateReservationService implements CreateReservationServiceInterface {
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

    @Autowired
    CircConfigRepository circConfigRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    LibraryMemberService libraryMemberService;

    @Override
    public Object reserveBook(String authToken, String library, String record_id, String coderId) {
        Member member = libraryMemberService.checkIfMemberExists(authToken);
        if (member == null) return ReservationsConstants.UNKNOWNMEMBER;

        String alreadyReservedByMember = checkIfBookReserved(record_id, member);
        if (alreadyReservedByMember != null) return alreadyReservedByMember;

        String limitExceeded = checkIfLimitExceeded(member);
        if (limitExceeded != null) return limitExceeded;

        return checkIfReservationPossible(library, record_id, coderId, member);
    }

    private Object checkIfReservationPossible(String library, String record_id, String coderId, Member member) {
        Optional<Record> record = recordsRepository.findById(record_id);

        if (record.isPresent()) {
            List<Primerak> primerci = record.get().getPrimerci();

            if (primerci != null && primerci.size() > 0) {
                List<Primerak> borrowedBooks = getBorrowedBooksOnGivenLocation(primerci, library, coderId);

                if (borrowedBooks.size() == 0) {
                    return ReservationsConstants.NORESERVATION;
                } else {
                    return createNewReservation(member, record.get(), coderId);
                }
            }
        }
        return null;
    }

    private String checkIfLimitExceeded(Member member) {
        long numberOfCurrentReservations = member.getReservations().stream()
                .filter(reservation -> !reservation.isBookPickedUp())
                .count();

        if (numberOfCurrentReservations >= 3) {
            return ReservationsConstants.LIMITEXCEEDED;
        }
        return null;
    }

    private String checkIfBookReserved(String record_id, Member member) {
        long numberOfSameReservations = member.getReservations().stream()
                .filter(reservation -> reservation.getRecord_id().equals(record_id))
                .count();

        if (numberOfSameReservations > 0) {
            return ReservationsConstants.ALREADYRESERVED;
        }
        return null;
    }

    private List<Primerak> getBorrowedBooksOnGivenLocation(List<Primerak> primerci, String library, String coderId) {
        // Sifrarnici ItemStatus-a za trenutnu bibl
        Map<String, ItemStatus> itemStatusMap = itemStatusRepository.getCoders(library).stream()
                .collect(Collectors.toMap(ItemStatus::getCoder_id, sl -> sl));

        List<Primerak> filteredBooks = new ArrayList<>();
        for (Primerak primerak : primerci) {
            boolean sameLocation = locationService.isSameLocation(coderId, library, primerak);
            if (sameLocation) {
                boolean isBookBorrowed = isBookBorrowed(itemStatusMap, primerak);
                if (isBookBorrowed)
                    filteredBooks.add(primerak);
            }
        }
        return filteredBooks;
    }

    private boolean isBookBorrowed(Map<String, ItemStatus> itemStatusMap, Primerak primerak) {
        boolean bookBorrowed = false;
        ItemStatus is = itemStatusMap.get(primerak.getStatus());
        if (is != null && is.isLendable() && is.isShowable()) {
            ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(primerak.getInvBroj());
            if (ia.getBorrowed()) {
                bookBorrowed = true;
            }
        }
        return bookBorrowed;
    }

    private Reservation createNewReservation(Member member, Record record, String coderId) {
        ReservationInQueue reservationInQueue = addToQueue(member, record, coderId);
        addToMembersList(member, record, coderId, reservationInQueue);
        return reservationInQueue;
    }

    private void addToMembersList(Member member, Record record, String coderId,
                                             ReservationInQueue reservationInQueue) {
        ReservationOnProfile reservationOnProfile = new ReservationOnProfile();
        reservationOnProfile.set_id(String.valueOf(new ObjectId()));
        reservationOnProfile.setReservationDate(reservationInQueue.getReservationDate());
        reservationOnProfile.setRecord_id(record.get_id());
        reservationOnProfile.setCoderId(coderId);
        reservationOnProfile.setReservationStatus(ReservationStatus.WAITING_IN_QUEUE);

        member.appendReservation(reservationOnProfile);
        memberRepository.save(member);
    }

    private ReservationInQueue addToQueue(Member member, Record record, String coderId) {
        ReservationInQueue reservationInQueue = new ReservationInQueue();
        reservationInQueue.set_id(String.valueOf(new ObjectId()));
        reservationInQueue.setReservationDate(new Date());
        reservationInQueue.setUserId(member.getUserId());
        reservationInQueue.setCoderId(coderId);

        record.appendReservation(reservationInQueue);
        recordsRepository.save(record);
        return reservationInQueue;
    }
}
