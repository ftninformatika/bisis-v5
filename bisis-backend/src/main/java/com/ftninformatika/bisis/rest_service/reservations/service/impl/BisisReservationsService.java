package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.Texts;
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
import com.ftninformatika.bisis.rest_service.service.implementations.EmailService;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import com.ftninformatika.util.WorkCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

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

    @Autowired
    EmailService emailService;


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
    public ReservationDTO confirmReservation(String reservation_id, String record_id, String ctlgNo) {
        Optional<Record> record = recordsRepository.findById(record_id);
        if (record.isPresent() && record.get().getReservations() != null && record.get().getReservations().size() > 0) {
            Iterator<ReservationInQueue> iter = record.get().getReservations().iterator();
            while (iter.hasNext()) {
                ReservationInQueue r = iter.next();
                if (r.get_id() != null && r.get_id().equals(reservation_id)) {
                    iter.remove();
                    recordsRepository.save(record.get());
                    setItemStatusReserved(ctlgNo);
                    return changeStatusToAssignedBook(record.get(), r.getUserId(), ctlgNo);
                }
            }
        }
        return null;
    }

    @Override
    public ReservationDTO getCurrentAssignedReservation(String userId, String ctlgNo) {
        Member member = memberRepository.getMemberByUserId(userId);
        for (ReservationOnProfile reservation : member.getReservations()) {
            if (reservation.getCtlgNo() != null && reservation.getCtlgNo().equals(ctlgNo) && reservation.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                Record record = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
                Book book = opacSearchService.getBookByRec(record);
                return getCurentReservationDTO(userId, ctlgNo, member, reservation, book);
            }
        }
        return null;
    }


    @Override
    @Transactional
    public ItemAvailability finishReservationProcess(ItemAvailability ia, Member member) {
        ia.setReserved(false);
        itemAvailabilityRepository.save(ia);

        for (ReservationOnProfile r : member.getReservations()) {
            if (r.getCtlgNo() != null && r.getCtlgNo().equals(ia.getCtlgNo()) && r.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                r.setReservationStatus(ReservationStatus.PICKED_UP);
                r.setBookPickedUp(true);
                memberRepository.save(member);
            }
        }
        return ia;
    }


    @Override
    @Transactional
    public ReservationDTO getNextReservation(String userId, String ctlgNo, String library) {
        deleteExpiredReservation(userId, ctlgNo, library);

        List<String> currentBook = new ArrayList<>();
        currentBook.add(ctlgNo);

        List<ReservationDTO> reservationDTOs = getReservationsForReturnedBooks(currentBook, library);

        if (reservationDTOs != null && reservationDTOs.size() > 0) {
            return reservationDTOs.get(0);
        } else {
            setItemStatusNotReserved(ctlgNo);
        }
        return null;
    }

    private void deleteExpiredReservation(String userId, String ctlgNo, String library) {
        Member currentAssigned = memberRepository.getMemberByUserId(userId);
        Record record = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
        String locationCode = locationService.getLocationCodeByPrimerak(record, ctlgNo, library);
        Iterator<ReservationOnProfile> iter = currentAssigned.getReservations().iterator();

        while (iter.hasNext()) {
            ReservationOnProfile r = iter.next();
            if (r.getCtlgNo() != null && r.getCtlgNo().equals(ctlgNo) && r.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                iter.remove();
            // slucaj kad knjiga nije dodeljena - obrise sa profila i obrise sa recorda
            } else if (r.getRecord_id().equals(record.get_id()) && !r.isBookPickedUp()
                    && r.getCoderId().equals(locationCode)) {
                iter.remove();
                deleteFirstInQueue(record, locationCode);
            }
            memberRepository.save(currentAssigned);
        }
    }

    private void deleteFirstInQueue(Record record, String locationCode) {
        ReservationInQueue firstReservation = getFirstByLocation(record, locationCode);
        record.getReservations().remove(firstReservation);
        recordsRepository.save(record);
    }

    private void setItemStatusReserved(String ctlgNo) {
        ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(ctlgNo);
        ia.setReserved(true);
        itemAvailabilityRepository.save(ia);
    }

    private void setItemStatusNotReserved(String ctlgNo) {
        ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(ctlgNo);
        ia.setReserved(false);
        itemAvailabilityRepository.save(ia);
    }

    private ReservationDTO changeStatusToAssignedBook(Record record, String userId, String ctlgNo) {
        Member member = memberRepository.getMemberByUserId(userId);
        for (ReservationOnProfile reservationOnProfile : member.getReservations()) {
            if (reservationOnProfile.getRecord_id().equals(record.get_id()) &&
                    reservationOnProfile.getReservationStatus().equals(ReservationStatus.WAITING_IN_QUEUE)) {

                reservationOnProfile.setPickUpDeadline(WorkCalendar.nextWorkDaysDate(new Date(), 3));
                reservationOnProfile.setReservationStatus(ReservationStatus.ASSIGNED_BOOK);
                reservationOnProfile.setCtlgNo(ctlgNo);
                memberRepository.save(member);

                sendEmail(member, record, reservationOnProfile.getPickUpDeadline());

                Book book = opacSearchService.getBookByRec(record);
                return getCurentReservationDTO(userId, ctlgNo, member, reservationOnProfile, book);
            }
        }
        return null;
    }

    private void sendEmail(Member member, Record record, Date deadline) {
        Book book = opacSearchService.getBookByRec(record);

        LibraryMember libraryMember = libraryMemberRepository.findByUsername(member.getEmail());
        emailService.sendSimpleMail(libraryMember.getUsername(), Texts.getString("RESERVATION_CONFIRMED_HEADING"),
                MessageFormat.format(Texts.getString("RESERVATION_CONFIRMED_BODY.0"), book.getTitle(), deadline));

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

    private ReservationDTO getCurentReservationDTO(String userId, String ctlgNo, Member member, ReservationOnProfile reservation, Book book) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCtlgNo(ctlgNo);
        reservationDTO.setTitle(book.getTitle());
        reservationDTO.setAuthors(book.getAuthors());
        reservationDTO.setMemberLastName(member.getLastName());
        reservationDTO.setPickUpDeadline(reservation.getPickUpDeadline());
        reservationDTO.setMemberFirstName(member.getFirstName());
        reservationDTO.setUserId(userId);
        reservationDTO.setReservationStatus(reservation.getReservationStatus());
        return reservationDTO;
    }
}
