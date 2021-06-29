package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.ReservationsGroup;
import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.reports.ReservedBook;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
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
    public ReservationsReport getReservationsReport(Date start, Date end, String library) {
        Collection<ReservationsGroup> inQueue = getReservationsInQueue(library);
        calculateTotalOnLocation(inQueue);

        List<Member> members1 = memberRepository.findMembersWithAssignedReservations();
        System.out.println("Ukupno members za assigned " + members1.size());
        Collection<ReservationsGroup> assigned = getReservationsFromMember(members1, library, ReservationStatus.ASSIGNED_BOOK);
        calculateTotalOnLocation(assigned);

        List<Member> members2 = memberRepository.findMembersWithPickedUpReservations();
        System.out.println("Ukupno members za picked up " + members2.size());
        Collection<ReservationsGroup> pickedUp = getReservationsFromMember(members2, library, ReservationStatus.PICKED_UP);
        calculateTotalOnLocation(pickedUp);

        ReservationsReport reservationsReport = new ReservationsReport();
        reservationsReport.setReservationsInQueue(inQueue);
        reservationsReport.setAssignedReservations(assigned);
        reservationsReport.setPickedUpReservations(pickedUp);

        return reservationsReport;
    }

    private void calculateTotalOnLocation(Collection<ReservationsGroup> rgroup) {
        for (ReservationsGroup rg : rgroup) {
            rg.calculateTotal();
        }
    }

    private Collection<ReservationsGroup> getReservationsInQueue(String library) {
        List<Record> records = recordsRepository.getAllRecordsWithReservations();
        HashMap<String, ReservationsGroup> result = new HashMap<>();
        boolean bookDtoCreated = false;
        int brojRez = 0;

        for (Record record : records) {
            brojRez += record.getReservations().size();
            for (ReservationInQueue r : record.getReservations()) {
                if (result.containsKey(r.getCoderId())) {
                    ReservationsGroup rg = result.get(r.getCoderId());

                    for (ReservedBook reservedBook : rg.getReservedBooks()) {
                        // if ReservedBookDTO is already created
                        if (reservedBook.getRecordId().equals(record.get_id())) {
                            reservedBook.setTotalCount(reservedBook.getTotalCount() + 1);
                            bookDtoCreated = true;
                            break;
                        }
                    }

                    if (!bookDtoCreated) {
                        rg.getReservedBooks().add(createReservedBookDTO(record));
                    }
                } else {
                    ReservationsGroup rg = new ReservationsGroup();
                    rg.setLocation(this.locationService.getLibraryBranchName(library, r.getCoderId()));
                    rg.getReservedBooks().add(createReservedBookDTO(record));
                    result.put(r.getCoderId(), rg);
                }
                bookDtoCreated = false;
            }
        }
        System.out.println("Broj rezervacija na cekanju: " + brojRez);
        racunajUkupno(result);
        return result.values();
    }

    public Collection<ReservationsGroup> getReservationsFromMember(List<Member> members, String library, ReservationStatus status) {
        HashMap<String, ReservationsGroup> result = new HashMap<>();
        boolean bookDtoCreated = false;
        int reNum = 0;

        for (Member member : members) {
            for (ReservationOnProfile r : member.getReservations()) {
                if (r.getReservationStatus().equals(status)) {
                    reNum += 1;
                    Optional<Record> record = recordsRepository.findById(r.getRecord_id());
                    if (record.isPresent()) {

                        // if location exists in hashmap
                        if (result.containsKey(r.getCoderId())) {
                            ReservationsGroup rg = result.get(r.getCoderId());

                            for (ReservedBook reservedBook : rg.getReservedBooks()) {
                                // if ReservedBookDTO is already created
                                if (reservedBook.getRecordId().equals(r.getRecord_id())) {
                                    reservedBook.setTotalCount(reservedBook.getTotalCount() + 1);
                                    bookDtoCreated = true;
                                    break;
                                }
                            }
                            if (!bookDtoCreated) {
                                rg.getReservedBooks().add(createReservedBookDTO(record.get()));
                            }
                        }

                        // if location doesnt exist in hashmap
                        else {
                            ReservationsGroup reservationsGroup = new ReservationsGroup();
                            reservationsGroup.setLocation(this.locationService.getLibraryBranchName(library, r.getCoderId()));
                            reservationsGroup.getReservedBooks().add(createReservedBookDTO(record.get()));
                            result.put(r.getCoderId(), reservationsGroup);
                        }
                        bookDtoCreated = false;
                    }
                }
            }
        }
        System.out.println("Broj dodeljenih rezervacija:" + reNum);
        racunajUkupno(result);
        return result.values();
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
        return ReservedBook.createReservedBookDTO(book);
    }
}
