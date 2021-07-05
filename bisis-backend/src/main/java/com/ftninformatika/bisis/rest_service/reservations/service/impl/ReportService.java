package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.ReservationsGroup;
import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.reports.ReservedBook;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
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
        Collection<ReservationsGroup> inQueue = getFromQueue(library, start, end);
        calculateTotalOnLocation(inQueue);

        ReservationsReport reservationsReport = new ReservationsReport();
        reservationsReport.setReservations(inQueue);
        return reservationsReport;
    }

    @Override
    public ReservationsReport getAssignedReservations(Date start, Date end, String library) {
        List<Member> members = memberRepository.findMembersWithReservations(ReservationStatus.ASSIGNED_BOOK, start, end);
        System.out.println("Ukupno members za assigned " + members.size());
        Collection<ReservationsGroup> assigned = getFromMember(members, library, ReservationStatus.ASSIGNED_BOOK, start, end);
        calculateTotalOnLocation(assigned);

        ReservationsReport reservationsReport = new ReservationsReport();
        reservationsReport.setReservations(assigned);
        return reservationsReport;

    }

    @Override
    public ReservationsReport getPickedUpReservations(Date start, Date end, String library) {
        List<Member> members = memberRepository.findMembersWithReservations(ReservationStatus.PICKED_UP, start, end);
        System.out.println("Ukupno members za picked up " + members.size());
        Collection<ReservationsGroup> pickedUp = getFromMember(members, library, ReservationStatus.PICKED_UP, start, end);
        calculateTotalOnLocation(pickedUp);

        ReservationsReport reservationsReport = new ReservationsReport();
        reservationsReport.setReservations(pickedUp);
        return reservationsReport;

    }

    private void calculateTotalOnLocation(Collection<ReservationsGroup> rgroup) {
        for (ReservationsGroup rg : rgroup) {
            rg.calculateTotal();
            Collections.sort(rg.getReservedBooks());
        }
    }

    private Collection<ReservationsGroup> getFromQueue(String library, Date start, Date end) {
        List<Record> records = recordsRepository.getAllRecordsWithReservations(start, end);
        HashMap<String, ReservationsGroup> result = new HashMap<>();
        int brojRez = 0;

        for (Record record : records) {
            brojRez += record.getReservations().size();
            for (ReservationInQueue r : record.getReservations()) {
                if (r.getReservationDate().after(start) && r.getReservationDate().before(end)) {
                    addReservedBookToResult(library, r.getCoderId(), result, record);
                }
            }
        }
        System.out.println("Broj rezervacija na cekanju: " + brojRez);
        racunajUkupno(result);
        return result.values();
    }

    public Collection<ReservationsGroup> getFromMember(List<Member> members, String library, ReservationStatus status, Date start, Date end) {
        HashMap<String, ReservationsGroup> result = new HashMap<>();
        int reNum = 0;

        for (Member member : members) {
            for (ReservationOnProfile r : member.getReservations()) {
                if (r.getReservationStatus().equals(status) && r.getReservationDate().after(start) && r.getReservationDate().before(end)) {
                    reNum += 1;
                    Optional<Record> record = recordsRepository.findById(r.getRecord_id());
                    if (record.isPresent()) {
                        addReservedBookToResult(library, r.getCoderId(), result, record.get());
                    }
                }
            }
        }
        System.out.println("Broj dodeljenih rezervacija:" + reNum);
        racunajUkupno(result);
        return result.values();
    }

    private void addReservedBookToResult(String library, String coderId, HashMap<String, ReservationsGroup> result,
                                         Record record) {
        // if location exists in the hashmap
        if (result.containsKey(coderId)) {
            ReservationsGroup rg = result.get(coderId);
            addNewReservedBook(record, rg);
        } else {
            createNewReservationsGroup(library, coderId, result, record);
        }
    }

    private void addNewReservedBook(Record record, ReservationsGroup rg) {
        boolean bookDtoCreated = false;

        for (ReservedBook reservedBook : rg.getReservedBooks()) {
            if (reservedBook.getRecordId().equals(record.get_id())) {
                reservedBook.setTotalCount(reservedBook.getTotalCount() + 1);
                bookDtoCreated = true;
                break;
            }
        }
        if (!bookDtoCreated) {
            rg.getReservedBooks().add(createReservedBookDTO(record));
        }
    }

    private void createNewReservationsGroup(String library, String coderId, HashMap<String, ReservationsGroup> result,
                                            Record record) {
        ReservationsGroup reservationsGroup = new ReservationsGroup();
        reservationsGroup.setLocation(this.locationService.getLibraryBranchName(library, coderId));
        reservationsGroup.getReservedBooks().add(createReservedBookDTO(record));
        result.put(coderId, reservationsGroup);
    }

    private void racunajUkupno(HashMap<String, ReservationsGroup> result) {
        int suma = 0;
        for (ReservationsGroup rg : result.values()) {
            for (ReservedBook r : rg.getReservedBooks()) {
                suma += r.getTotalCount();
            }
        }
        System.out.println("Suma: " + suma);
    }

    private ReservedBook createReservedBookDTO(Record record) {
        Book book = opacSearchService.getBookByRec(record);
        return ReservedBook.createReservedBookDTO(book, record.getRN());
    }
}
