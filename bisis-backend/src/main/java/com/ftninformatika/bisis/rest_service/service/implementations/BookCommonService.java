package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author badf00d21  27.9.19.
 */
@Service
public class BookCommonService {

    @Autowired BookCommonRepository bookCommonRepository;
    @Autowired RecordsRepository recordsRepository;
    @Autowired OpacSearchService opacSearchService;

    public BookCommon saveModifyBookCommon(BookCommon bookCommon) {
        if (bookCommon == null || bookCommon.getUid() == null ||
                (bookCommon.getIsbn() == null && bookCommon.getIssn() == null)) return null;
        return bookCommonRepository.save(bookCommon);
    }

    public BookCommon getBookCommonByUID(Integer bookCommonUID) {
        return bookCommonRepository.findByUid(bookCommonUID);
    }

    public List<Book> getBooksByRecordIds(List<String> recordsIds) {
        List<Book> books = new ArrayList<>();
        Iterator<Record> recordIterator = recordsRepository.findAllById(recordsIds).iterator();
        if (recordIterator.hasNext()) {
            Book b = opacSearchService.getBookByRec(recordIterator.next());
            books.add(b);
        }
        return books;
    }

}
