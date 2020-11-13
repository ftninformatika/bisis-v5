package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
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
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.BisisReservationsServiceInterface;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.LocationServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author marijakovacevic
 */

@Service
public class BisisReservationsService implements BisisReservationsServiceInterface {
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
    public List<ReservationDTO> getReservationsForReturnedBooks(List<String> returnedBooks, String library) {
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (String returnedBook : returnedBooks) {
            Record record = recordsRepository.getRecordByPrimerakInvNum(returnedBook);
            String locationCode = locationService.getLocationCodeByPrimerak(record, returnedBook, library);
            ReservationInQueue firstReservation = getFirstByLocation(record, locationCode);

            if (firstReservation != null) {
                // get additional data from the book and the member who reserved it
                Book book = opacSearchService.getBookByRec(record);
                Member member = memberRepository.getMemberByUserId(firstReservation.getUserId());

                ReservationDTO reservationDTO = ReservationDTO.convertFirstReservationToDto(firstReservation, returnedBook,
                        book, member, locationCode);
                reservationDTOS.add(reservationDTO);
            }
        }
        return reservationDTOS;
    }

    @Override
    @Transactional
    public boolean confirmReservation(String reservation_id, String record_id, String ctlgNo) {
        Optional<Record> record = recordsRepository.findById(record_id);
        if (record.isPresent() && record.get().getReservations() != null && record.get().getReservations().size() > 0) {
            for (ReservationInQueue reservationInQueue : record.get().getReservations()) {
                if (reservationInQueue.get_id().equals(reservation_id)) {
                    record.get().getReservations().remove(reservationInQueue);
                    recordsRepository.save(record.get());
                    setItemStatusReserved(ctlgNo);
                    if (changeStatusToAssignedBook(record_id, reservationInQueue.getUserId())) return true;
                }
            }
        }
        return  false;
    }

    private void setItemStatusReserved(String ctlgNo) {
        ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(ctlgNo);
        ia.setReserved(true);
        itemAvailabilityRepository.save(ia);
    }

    private boolean changeStatusToAssignedBook(String record_id, String userId) {
        Member member = memberRepository.getMemberByUserId(userId);
        for (ReservationOnProfile reservationOnProfile : member.getReservations()) {
            if (reservationOnProfile.getRecord_id().equals(record_id) &&
                    reservationOnProfile.getReservationStatus().equals(ReservationStatus.WAITING_IN_QUEUE)) {

                reservationOnProfile.setPickUpDeadline(new Date());   // todo +3 working days
                reservationOnProfile.setReservationStatus(ReservationStatus.ASSIGNED_BOOK);
                memberRepository.save(member);
                return true;
            }
        }
        return false;
    }

    public ReservationInQueue getFirstByLocation(Record record, String locationCode) {
        if (record.getReservations() != null && record.getReservations().size() > 0) {
            for (ReservationInQueue reservationInQueue : record.getReservations()) {
                if (reservationInQueue.getCoderId().equals(locationCode)) {
                    return reservationInQueue;
                }
            }
        }
        return null;
    }
}
