package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.CircConfigRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.LocationServiceInterface;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.OpacReservationsServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author marijakovacevic
 */

@Service
public class OpacReservationsService implements OpacReservationsServiceInterface {
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
    LocationServiceInterface locationService;

    @Autowired
    LibraryMemberService libraryMemberService;

    @Override
    public List<ReservationDTO> getReservationsByUser(String library, String authToken) {
        Member member = libraryMemberService.checkIfMemberExists(authToken);
        if (member == null) return null;

        List<ReservationOnProfile> reservations = member.getReservations();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();

        for (ReservationOnProfile reservation : reservations) {
            if (!reservation.isBookPickedUp()) {
                ReservationDTO reservationDTO = createReservationDTO(library, reservation);
                reservationDTOS.add(reservationDTO);
            }
        }
        return reservationDTOS;
    }

    private ReservationDTO createReservationDTO(String library, ReservationOnProfile reservation) {
        Optional<Record> record = recordsRepository.findById(reservation.getRecord_id());
        if (record.isPresent()) {
            String libraryBranchName = locationService.getLibraryBranchName(library, reservation.getCoderId());
            // get a book for a given record to extract title and authors
            Book book = opacSearchService.getBookByRec(record.get());
            return ReservationDTO.convertToDto(reservation, book, libraryBranchName);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteReservation(String authToken, String reservationId) {
        Member member = libraryMemberService.checkIfMemberExists(authToken);
        if (member == null) return false;

        String record_id = deleteFromMembersList(reservationId, member);

        return deleteFromQueue(member, record_id);
    }

    public boolean deleteFromQueue(Member member, String record_id) {
        Optional<Record> record = recordsRepository.findById(record_id);
        if (record.isPresent()) {
            LinkedList<ReservationInQueue> reservations = record.get().getReservations();
            reservations.removeIf(pr -> pr.getUserId().equals(member.getUserId()));
            recordsRepository.save(record.get());
            return true;
        }
        return false;
    }

    public String deleteFromMembersList(String reservationId, Member member) {
        String record_id = "";
        List<ReservationOnProfile> membersReservations = member.getReservations();
        Iterator<ReservationOnProfile> iter = membersReservations.iterator();

        while (iter.hasNext()) {
            ReservationOnProfile reservationOnProfile = iter.next();
            if (reservationOnProfile.get_id().equals(reservationId)) {
                record_id = reservationOnProfile.getRecord_id();
                iter.remove();
                memberRepository.save(member);
                return record_id;
            }
        }
        return record_id;
    }

    public Boolean isReservationsQueueEmpty(String ctlgNo) {
        Record record = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
        return record.getReservations() != null && record.getReservations().size() == 0;
    }
}
