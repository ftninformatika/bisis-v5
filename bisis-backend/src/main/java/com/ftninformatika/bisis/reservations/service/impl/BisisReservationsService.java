package com.ftninformatika.bisis.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.dto.ReservationInQueueDTO;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.dto.ReservationDTO;
import com.ftninformatika.bisis.opac.members.LibraryMember;
import com.ftninformatika.bisis.opac.service.NotificationService;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.Texts;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.core.repositories.CircConfigRepository;
import com.ftninformatika.bisis.reservations.service.interfaces.BisisReservationsServiceInterface;
import com.ftninformatika.bisis.reservations.service.interfaces.LocationServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.EmailService;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import com.ftninformatika.util.WorkCalendar;
import com.ftninformatika.utils.constants.ReservationsConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author marijakovacevic
 */

@Service
public class BisisReservationsService implements BisisReservationsServiceInterface {
    private Logger log = Logger.getLogger(BisisReservationsService.class);

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

    @Autowired
    EmailService emailService;

    @Autowired
    CreateReservationService createReservationService;

    @Autowired
    OpacReservationsService opacReservationsService;

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Autowired
    NotificationService notificationService;


    @Override
    public List<ReservationDTO> getReservationsForReturnedBooks(List<String> returnedBooks, String library) {
        log.info("(getReservationsForReturnedBooks)");

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
    public ReservationDTO confirmReservation(String reservation_id, String record_id, String ctlgNo, String library) {
        Optional<Record> record = recordsRepository.findById(record_id);
        if (record.isPresent() && record.get().getReservations() != null && record.get().getReservations().size() > 0) {
            Iterator<ReservationInQueue> iter = record.get().getReservations().iterator();
            while (iter.hasNext()) {
                ReservationInQueue r = iter.next();
                if (r.get_id() != null && r.get_id().equals(reservation_id)) {
                    iter.remove();
                    recordsRepository.save(record.get());

                    log.info("(confirmReservation) - iz zapisa: " + record_id + " je obrisana rezervacija: " + reservation_id);

                    ReservationDTO reservationDTO = changeStatusToAssignedBook(record.get(), r.getUserId(), ctlgNo, library);
                    if (reservationDTO != null) {
                        setItemStatusReserved(ctlgNo);
                    }
                    return reservationDTO;
                }
            }
        }
        return null;
    }

    @Override
    public ReservationDTO getCurrentAssignedReservation(String userId, String ctlgNo) {
        log.info("(getCurrentAssignedReservation) - dobavljanje trenutno dodeljenog za ctlgNo: " + ctlgNo);

        Member member = memberRepository.getMemberByUserId(userId);
        for (ReservationOnProfile reservation : member.getReservations()) {
            if (reservation.getCtlgNo() != null && reservation.getCtlgNo().equals(ctlgNo) && reservation.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                Record record = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
                Book book = opacSearchService.getBookByRec(record);
                return getCurentReservationDTO(userId, ctlgNo, member, reservation, book, true);
            }
        }
        return null;
    }


    @Override
    @Transactional
    public ItemAvailability finishReservationProcess(ItemAvailability ia, Member member) {
        log.info("(finishReservationProcess) - knjiga: " + ia.getCtlgNo() + " je rezervisana i vrši se zaduživanje za člana: " + member.get_id());

        ia.setReserved(false);
        itemAvailabilityRepository.save(ia);

        log.info("(finishReservationProcess) - status za ia (ctlgNo: " + ia.getCtlgNo() + ") je promenjen na reserved=false");

        for (ReservationOnProfile r : member.getReservations()) {
            if (r.getCtlgNo() != null && r.getCtlgNo().equals(ia.getCtlgNo()) && r.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                r.setReservationStatus(ReservationStatus.PICKED_UP);
                r.setBookPickedUp(true);
                memberRepository.save(member);

                log.info("(finishReservationProcess) - status rezervacije: " + r.get_id() + " je postao PICKED_UP. " +
                        "Član za kog se zadužuje: " + member.get_id() + " je kreirao rezervaciju i preuzeo knjigu: " + r.getCtlgNo());
            }
        }
        return ia;
    }


    @Override
    @Transactional
    public ReservationDTO getNextReservation(String userId, String ctlgNo, String library) {
        log.info("(getNextReservation) - provera da li postoji još rezervacija za: " + ctlgNo + " u biblioteci: " + library);

        deleteExpiredReservation(userId, ctlgNo, library);

        List<String> currentBook = new ArrayList<>();
        currentBook.add(ctlgNo);

        List<ReservationDTO> reservationDTOs = getReservationsForReturnedBooks(currentBook, library);

        if (reservationDTOs != null && reservationDTOs.size() > 0) {
            log.info("(getNextReservation) - ima još rezervacija za ctlgNo: " + ctlgNo + " u biblioteci: " + library);
            return reservationDTOs.get(0);
        } else {
            log.info("(getNextReservation) - nema više rezervacija za ctlgNo: " + ctlgNo + " u biblioteci: " + library);
            setItemStatusNotReserved(ctlgNo);
        }
        return null;
    }

    @Override
    @Transactional
    public HashMap<String, String> updateReservations(String library, HashMap<String, String> books,
                                                      List<ReservationOnProfile> reservationsToCancel, Member member) {
        // if there are reservations to delete, delete them
        cancelReservations(reservationsToCancel, member);

        // if there are books to reserve, reserve them
        HashMap<String, String> reservationsResult = new HashMap<>();
        if (books != null && !books.isEmpty()) {
            for (Map.Entry<String, String> pair : books.entrySet()) {
                Optional<Record> record = recordsRepository.findById(pair.getKey());
                if (record.isPresent()) {

                    log.info("(updateReservations) - rezervisanje knjige iz BISIS-a");
                    // in BISIS its already checked if the reservation limit is exceeded & if the book is already borrowed
                    // check if there are borrowed books on the given location
                    Object reservation = createReservationService.checkIfReservationPossible(library, record.get().get_id(), pair.getValue(), member);
                    if (reservation != null) {
                        reservationsResult.put(record.get().get_id(), getReservationResult(reservation));
                    }
                }
            }
        }
        return reservationsResult;
    }

    @Override
    public List<ReservationInQueueDTO> getReservationsByRecord(String library, String record_id) {
        Optional<Record> record = recordsRepository.findById(record_id);
        List<ReservationInQueueDTO> reservations = new ArrayList<>();
        if (record.isPresent()) {
            if (record.get().getReservations() != null) {
                for (ReservationInQueue reservation : record.get().getReservations()) {
                    String firstName = "";
                    String lastname = "";

                    Member m = memberRepository.getMemberByUserId(reservation.getUserId());
                    if (m != null) {
                        firstName = m.getFirstName();
                        lastname = m.getLastName();
                    }
                    String location = locationService.getLibraryBranchName(library, reservation.getCoderId());

                    ReservationInQueueDTO reservationInQueueDTO = new ReservationInQueueDTO(reservation.getUserId(),
                            firstName, lastname, location, reservation.getCoderId(), formatDate(reservation.getReservationDate()));
                    reservations.add(reservationInQueueDTO);
                }
            }
        }
        return reservations;
    }

    private String getReservationResult(Object reservation) {
        if (reservation.equals(ReservationsConstants.NO_RESERVATION)) {
            return ReservationsConstants.NO_RESERVATION;
        } else {
            return ReservationsConstants.SUCCESS;
        }
    }

    private void cancelReservations(List<ReservationOnProfile> reservations, Member member) {
        if (reservations != null) {
            for (ReservationOnProfile reservation : reservations) {
                String record_id = opacReservationsService.deleteFromMembersList(reservation.get_id(), member);
                opacReservationsService.deleteFromQueue(member, record_id);

                log.info("(cancelReservations) - obrisana rezervacija iz BISIS-a: " + reservation.get_id() + " za zapis: "
                        + record_id + " i clana: " + member.get_id());
            }
        }
    }

    private void deleteExpiredReservation(String userId, String ctlgNo, String library) {
        log.info("(deleteExpiredReservation) - brisanje trenutne rezervacije");

        Member currentAssigned = memberRepository.getMemberByUserId(userId);
        Record record = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
        String locationCode = locationService.getLocationCodeByPrimerak(record, ctlgNo, library);
        Iterator<ReservationOnProfile> iter = currentAssigned.getReservations().iterator();

        while (iter.hasNext()) {
            ReservationOnProfile r = iter.next();
            if (r.getCtlgNo() != null && r.getCtlgNo().equals(ctlgNo) && r.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                log.info("(deleteExpiredReservation) - knjiga je dodeljena, ali je član nije preuzeo. Briše se iz liste člana: "
                        + currentAssigned.get_id());
                iter.remove();

                // slucaj kad knjiga nije dodeljena - obrise sa profila i obrise sa recorda
            } else if (r.getRecord_id().equals(record.get_id()) && !r.isBookPickedUp()
                    && r.getCoderId().equals(locationCode)) {
                log.info("(deleteExpiredReservation) - knjiga nije dodeljena. Briše se iz liste člana: "
                        + currentAssigned.get_id() + " i iz liste u okviru zapisa: " + record.get_id());

                iter.remove();
                deleteFirstInQueue(record, locationCode);
            }
            memberRepository.save(currentAssigned);
        }
    }

    private void deleteFirstInQueue(Record record, String locationCode) {
        log.info("(deleteFirstInQueue) - brisanje prve rezervacije iz zapisa: " + record.get_id() + ", location code: " + locationCode);

        ReservationInQueue firstReservation = getFirstByLocation(record, locationCode);
        record.getReservations().remove(firstReservation);
        recordsRepository.save(record);
    }

    private void setItemStatusReserved(String ctlgNo) {
        ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(ctlgNo);
        ia.setReserved(true);
        itemAvailabilityRepository.save(ia);

        log.info("(setItemStatusReserved) - status za ia (ctlgNo: " + ctlgNo + ") je promenjen na reserved=true");
    }

    private void setItemStatusNotReserved(String ctlgNo) {
        ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(ctlgNo);
        ia.setReserved(false);
        itemAvailabilityRepository.save(ia);

        log.info("(setItemStatusNotReserved) - status za ia (ctlgNo: " + ctlgNo + ") je promenjen na reserved=false");
    }

    private ReservationDTO changeStatusToAssignedBook(Record record, String userId, String ctlgNo, String library) {
        Member member = memberRepository.getMemberByUserId(userId);
        for (ReservationOnProfile reservationOnProfile : member.getReservations()) {
            if (reservationOnProfile.getRecord_id().equals(record.get_id()) &&
                    reservationOnProfile.getReservationStatus().equals(ReservationStatus.WAITING_IN_QUEUE)) {

                reservationOnProfile.setPickUpDeadline(WorkCalendar.nextWorkDaysDate(new Date(), 3));
                reservationOnProfile.setReservationStatus(ReservationStatus.ASSIGNED_BOOK);
                reservationOnProfile.setCtlgNo(ctlgNo);
                memberRepository.save(member);

                log.info("(changeStatusToAssignedBook) - u listi rezervacija korisnika: " + member.get_id() + " promenjen je status" +
                        " rezervacije sa WAITING_IN_QUEUE na ASSIGNED_BOOK i dodeljen je ctlgNo: " + ctlgNo);

                Boolean emailSent = sendEmail(member, record, reservationOnProfile.getPickUpDeadline(), library);

                Book book = opacSearchService.getBookByRec(record);

                return getCurentReservationDTO(userId, ctlgNo, member, reservationOnProfile, book, emailSent);
            }
        }
        return null;
    }


    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
        return sdf.format(date);
    }


    private Boolean sendEmail(Member member, Record record, Date deadline, String library) {
        boolean emailSent = false;
        Book book = opacSearchService.getBookByRec(record);
        String formattedDate = formatDate(deadline);
        LibraryMember libraryMember = libraryMemberRepository.findByIndex(member.get_id());
        if (libraryMember != null && libraryMember.getUsername() != null && !libraryMember.getUsername().equals("")) {
            LibraryConfiguration libConf = libraryConfigurationRepository.getByLibraryName(library);
            emailService.sendReservationConfirmation(libraryMember.getUsername(), book.getTitle(), formattedDate, libConf);
            try {
                notificationService.sendMessageToUsername(libraryMember.getUsername(),
                        Texts.getString("RESERVATION_CONFIRMED_HEADING"),
                        "Преузмите Вашу резервисану књигу " + book.getTitle() + " у року од 3 радна дана", "reservation");
            } catch (Exception e) {
                e.printStackTrace();
            }
            emailSent = true;

            log.info("(sendEmail) - član: " + member.get_id() + " ima nalog na OPAC-u i email je uspesno poslat za naslov: " + book.getTitle());

        }

        if (!emailSent) {
            log.info("(sendEmail) - član nema nalog na OPAC-u: " + member.get_id() + " i email nije poslat za naslov: " + book.getTitle());
        }

        return emailSent;
    }

    public ReservationInQueue getFirstByLocation(Record record, String locationCode) {
        log.info("(getFirstByLocation) - record: " + record.get_id() + ", location code: " + locationCode);

        if (record.getReservations() != null && record.getReservations().size() > 0) {
            for (ReservationInQueue reservationInQueue : record.getReservations()) {
                if (reservationInQueue.getCoderId().equals(locationCode)) {
                    log.info("(getFirstByLocation) - postoji rezervacija za zapis: " + record.get_id() + ", za korisnika: " + reservationInQueue.getUserId());
                    return reservationInQueue;
                }
            }
        }
        return null;
    }

    private ReservationDTO getCurentReservationDTO(String userId, String ctlgNo, Member member,
                                                   ReservationOnProfile reservation, Book book, boolean emailSent) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setCtlgNo(ctlgNo);
        reservationDTO.setTitle(book.getTitle());
        reservationDTO.setAuthors(book.getAuthors());
        reservationDTO.setMemberLastName(member.getLastName());
        reservationDTO.setPickUpDeadline(reservation.getPickUpDeadline());
        reservationDTO.setMemberFirstName(member.getFirstName());
        reservationDTO.setUserId(userId);
        reservationDTO.setReservationStatus(reservation.getReservationStatus());
        reservationDTO.setEmailSent(emailSent);
        reservationDTO.setPhoneNumber(member.getPhone());
        return reservationDTO;
    }
}
