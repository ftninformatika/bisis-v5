package com.ftninformatika.bisis.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.dto.ReservationDTO;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.core.repositories.CircConfigRepository;
import com.ftninformatika.bisis.reservations.service.interfaces.LocationServiceInterface;
import com.ftninformatika.bisis.reservations.service.interfaces.OpacReservationsServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author marijakovacevic
 */

@Service
public class OpacReservationsService implements OpacReservationsServiceInterface {
    private Logger log = Logger.getLogger(OpacReservationsService.class);

    @Autowired
    LibraryMemberRepository libraryMemberRepository;

    @Autowired
    SubLocationRepository sublocationRepository;

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
    public List<ReservationDTO> getReservationsByUser(String library, String memberNo) {
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        Member member = memberRepository.getMemberByUserId(memberNo);

        if (member != null) {
            List<ReservationOnProfile> reservations = member.getReservations();
            for (ReservationOnProfile reservation : reservations) {
                if (!reservation.isBookPickedUp()) {
                    ReservationDTO reservationDTO = createReservationDTO(library, reservation);
                    reservationDTOS.add(reservationDTO);
                }
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
    public Boolean deleteReservation(String memberNo, String reservationId) {
        log.info("(deleteReservation) - Brisanje rezervacije, clan: " + memberNo + " rezervacija: " + reservationId);

        Member member = memberRepository.getMemberByUserId(memberNo);
        if (member != null) {
            String record_id = deleteFromMembersList(reservationId, member);
            return deleteFromQueue(member, record_id);
        } else {
            return false;
        }
    }

    public boolean deleteFromQueue(Member member, String record_id) {
        Optional<Record> record = recordsRepository.findById(record_id);
        if (record.isPresent()) {
            log.info("(deleteFromQueue) - iz zapisa: " + record_id + " se brise rezervacija korisnika: " + member.get_id());

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

                log.info("(deleteFromMembersList) - rezervacija za zapis: " + record_id + " je obrisana iz liste rezervacija korisnika: " + member.get_id());

                return record_id;
            }
        }
        return record_id;
    }

    public Boolean isReservationPresentOnLocation(String library, String ctlgNo) {
        Record record = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
        String locationCode = locationService.getLocationCodeByPrimerak(record, ctlgNo, library);

        if (record.getReservations() != null) {
            if (record.getReservations().size() > 0) {
                for (ReservationInQueue reservation : record.getReservations()) {
                    if (reservation.getCoderId().equals(locationCode)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
}
