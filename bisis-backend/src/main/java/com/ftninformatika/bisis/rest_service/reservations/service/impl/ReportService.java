package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.ReservationsGroup;
import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.reports.ReservedBook;
import com.ftninformatika.bisis.reservations.Reservation;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.ReportServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author marijakovacevic
 */
@Service
public class ReportService implements ReportServiceInterface {
    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    OpacSearchService opacSearchService;

    @Autowired
    LocationService locationService;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public ReservationsReport getReservationsFromQueue(Date start, Date end, String library) {
        return getReservationsReport(start, end, library, ReservationStatus.WAITING_IN_QUEUE);
    }

    @Override
    public ReservationsReport getAssignedReservations(Date start, Date end, String library) {
        return getReservationsReport(start, end, library, ReservationStatus.ASSIGNED_BOOK);
    }

    @Override
    public ReservationsReport getPickedUpReservations(Date start, Date end, String library) {
        return getReservationsReport(start, end, library, ReservationStatus.PICKED_UP);
    }

    private ReservationsReport getReservationsReport(Date start, Date end, String library, ReservationStatus status) {
        List<Member> members = memberRepository.findMembersWithReservationsByStatus(status, start, end);
        Collection<ReservationsGroup> reservations = getReservationsFromMember(members, library, status, start, end);

        calculateTotalOnLocation(reservations);
        List<ReservationsGroup> reservationsSorted = sortByLocation(reservations);

        ReservationsReport reservationsReport = new ReservationsReport();
        reservationsReport.setReservations(reservationsSorted);
        return reservationsReport;
    }

    @Override
    public Collection<ReservedBook> getAllByRecord(Date start, Date end, String library) {
        HashMap<String, ReservedBook> result = new HashMap<>();
        List<Member> members = memberRepository.findMembersWithReservations(start, end);

        for (Member member : members) {
            for (ReservationOnProfile reservation : member.getReservations()) {
                addReservedBookToResult(result, reservation);
            }
        }

        List<ReservedBook> list = new ArrayList<>(result.values());
        Collections.sort(list);
        return list;
    }

    private void addReservedBookToResult(HashMap<String, ReservedBook> result, ReservationOnProfile reservation) {
        if (result.containsKey(reservation.getRecord_id())) {
            ReservedBook rb = result.get(reservation.getRecord_id());
            rb.setTotalCount(rb.getTotalCount() + 1);
        } else {
            Optional<Record> record = recordsRepository.findById(reservation.getRecord_id());
            if (record.isPresent()) {
                ReservedBook rb = createReservedBookDTO(record.get());
                result.put(reservation.getRecord_id(), rb);
            }
        }
    }


    public Collection<ReservationsGroup> getReservationsFromMember(List<Member> members, String library, ReservationStatus status,
                                                                   Date start, Date end) {
        HashMap<String, ReservationsGroup> result = new HashMap<>();
        for (Member member : members) {
            for (ReservationOnProfile r : member.getReservations()) {
                if (r.getReservationStatus().equals(status) && r.getReservationDate().after(start) && r.getReservationDate().before(end)) {
                    Optional<Record> record = recordsRepository.findById(r.getRecord_id());
                    if (record.isPresent()) {
                        addReservedBookToResult(library, r, result, record.get(), member);
                    }
                }
            }
        }
        return result.values();
    }

    private void addReservedBookToResult(String library, Reservation reservation, HashMap<String, ReservationsGroup> result,
                                         Record record, Member member) {
        // if location exists in the hashmap
        if (result.containsKey(reservation.getCoderId())) {
            ReservationsGroup rg = result.get(reservation.getCoderId());
            addNewReservedBook(record, rg, reservation, member);
        } else {
            createNewReservationsGroup(library, reservation, result, record, member);
        }
    }

    private void addNewReservedBook(Record record, ReservationsGroup rg, Reservation reservation, Member member) {
        boolean bookDtoCreated = false;

        for (ReservedBook reservedBook : rg.getReservedBooks()) {
            if (reservedBook.getRecordId().equals(record.get_id())) {
                reservedBook.setTotalCount(reservedBook.getTotalCount() + 1);
                bookDtoCreated = true;
                break;
            }
        }
        if (!bookDtoCreated) {
            ReservedBook newReservedBook = createReservedBookDTO(record);
            newReservedBook.setAdditionalData(member, reservation);
            rg.getReservedBooks().add(newReservedBook);
        }
    }

    private void createNewReservationsGroup(String library, Reservation reservation, HashMap<String, ReservationsGroup> result,
                                            Record record, Member member) {
        ReservationsGroup reservationsGroup = new ReservationsGroup();
        reservationsGroup.setLocation(this.locationService.getLibraryBranchName(library, reservation.getCoderId()));

        ReservedBook newReservedBook = createReservedBookDTO(record);
        newReservedBook.setAdditionalData(member, reservation);
        reservationsGroup.getReservedBooks().add(newReservedBook);
        result.put(reservation.getCoderId(), reservationsGroup);
    }

    private void calculateTotalOnLocation(Collection<ReservationsGroup> rgroup) {
        for (ReservationsGroup rg : rgroup) {
            rg.calculateTotal();
            Collections.sort(rg.getReservedBooks());
        }
    }

    private List<ReservationsGroup> sortByLocation(Collection<ReservationsGroup> rgroup) {
        List<ReservationsGroup> list = new ArrayList<>(rgroup);
        Collections.sort(list);
        return list;
    }

    private ReservedBook createReservedBookDTO(Record record) {
        Book book = opacSearchService.getBookByRec(record);
        return new ReservedBook(book, record.getRN());
    }
}
