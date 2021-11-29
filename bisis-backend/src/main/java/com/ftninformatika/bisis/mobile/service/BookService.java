package com.ftninformatika.bisis.mobile.service;

import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author marijakovacevic
 */

@Service
public class BookService {

    @Autowired
    RecordsRepository recordsRepository;

    public boolean isArticle(Book book){
        Optional<Record> r = recordsRepository.findById(book.get_id());
        if (r.isPresent()){
            RecordPreview rp = new RecordPreview();
            return rp.isArticle(r.get());
        }
        return false;
    }
}
