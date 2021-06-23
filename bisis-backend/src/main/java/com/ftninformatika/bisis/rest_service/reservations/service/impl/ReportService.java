package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.ReservedBookDTO;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.ReportServiceInterface;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author marijakovacevic
 */
@Service
public class ReportService implements ReportServiceInterface {
    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    OpacSearchService opacSearchService;

    @Override
    public HashMap<String, List<ReservedBookDTO>> getReservationsReport(String library) {
        List<Record> records = recordsRepository.getAllRecordsWithReservations();
        HashMap<String, List<ReservedBookDTO>> result = new HashMap<>();
        boolean bookDtoCreated = false;

        for (Record record : records) {
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
            }
        }

        return result;
    }

    private ReservedBookDTO createReservedBookDTO(Record record) {
        Book book = opacSearchService.getBookByRec(record);
        ReservedBookDTO reservedBookDTO = new ReservedBookDTO();
        reservedBookDTO.setRecordId(book.get_id());
        reservedBookDTO.setTitle(book.getTitle());
        reservedBookDTO.setAuthors(book.getAuthors());
        reservedBookDTO.setPublisher(book.getPublisher());
        reservedBookDTO.setTotalCount(1);
        return reservedBookDTO;
    }
}
