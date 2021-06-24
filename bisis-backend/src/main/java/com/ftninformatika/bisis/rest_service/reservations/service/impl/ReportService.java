package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.reports.ReservedBookDTO;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.ReportServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    MemberRepository memberRepository;

    @Override
    public ReservationsReport getReservationsReport(String library) {
        HashMap<String, List<ReservedBookDTO>> reservationsInQueue = getReservationsInQueue();
        HashMap<String, List<ReservedBookDTO>> assignedReservations = getAssignedReservations();

        ReservationsReport reservationsReport = new ReservationsReport();
        reservationsReport.setReservationsInQueue(reservationsInQueue);
        reservationsReport.setAssignedReservations(assignedReservations);
        return reservationsReport;
    }

    private HashMap<String, List<ReservedBookDTO>> getReservationsInQueue() {
        List<Record> records = recordsRepository.getAllRecordsWithReservations();
        HashMap<String, List<ReservedBookDTO>> result = new HashMap<>();
        boolean bookDtoCreated = false;
        int brojRez = 0;

        for (Record record : records) {
            brojRez += record.getReservations().size();
            for (ReservationInQueue r : record.getReservations()) {
                if (result.containsKey(r.getCoderId())) {
                    List<ReservedBookDTO> books = result.get(r.getCoderId());

                    for (ReservedBookDTO reservedBookDTO : books) {
                        // if ReservedBookDTO is already created
                        if (reservedBookDTO.getRecordId().equals(record.get_id())) {
                            reservedBookDTO.setTotalCount(reservedBookDTO.getTotalCount() + 1);
                            bookDtoCreated = true;
                            break;
                        }
                    }

                    if (!bookDtoCreated) {
                        books.add(createReservedBookDTO(record));
                    }
                } else {
                    List<ReservedBookDTO> booksList = new ArrayList<>();
                    booksList.add(createReservedBookDTO(record));
                    result.put(r.getCoderId(), booksList);
                }
                bookDtoCreated = false;
            }
        }
        System.out.println("Broj rezervacija na cekanju: " + brojRez);
        racunajUkupno(result);
        return result;
    }

    public HashMap<String, List<ReservedBookDTO>> getAssignedReservations() {
        List<Member> members = memberRepository.getMemberWithAssignedReservation();
        HashMap<String, List<ReservedBookDTO>> result = new HashMap<>();
        int reNum = 0;

        for (Member member : members) {
            for (ReservationOnProfile reservation : member.getReservations()) {
                if (reservation.getReservationStatus().equals(ReservationStatus.ASSIGNED_BOOK)) {
                    reNum += 1;
                    Optional<Record> record = recordsRepository.findById(reservation.getRecord_id());
                    if (record.isPresent()) {

                        // if location exists in hashmap
                        if (result.containsKey(reservation.getCoderId())) {
                            List<ReservedBookDTO> booksList = result.get(reservation.getCoderId());
                            booksList.add(createReservedBookDTO(record.get()));
                        }

                        // if location doesnt exist in hashmap
                        else {
                            List<ReservedBookDTO> booksList = new ArrayList<>();
                            booksList.add(createReservedBookDTO(record.get()));
                            result.put(reservation.getCoderId(), booksList);
                        }
                    }
                }
            }
        }
        System.out.println("Broj dodeljenih rezervacija:" + reNum);
        racunajUkupno(result);
        return result;
    }

    private void racunajUkupno(HashMap<String, List<ReservedBookDTO>> result) {
        int suma = 0;
        for (List<ReservedBookDTO> reservedBooks : result.values()) {
            for (ReservedBookDTO r : reservedBooks) {
                suma += r.getTotalCount();
            }
        }
        System.out.println("Suma: " + suma);
    }

    private ReservedBookDTO createReservedBookDTO(Record record) {
        Book book = opacSearchService.getBookByRec(record);
        return ReservedBookDTO.createReservedBookDTO(book);
    }
}
