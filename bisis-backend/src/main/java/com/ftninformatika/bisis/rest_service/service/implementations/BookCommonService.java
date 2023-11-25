package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac.BookCollection;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.books.BookCommon;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.search.SearchModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author badf00d21  27.9.19.
 */
@Service
public class BookCommonService {

    @Autowired BookCommonRepository bookCommonRepository;
    @Autowired
    RecordsRepository recordsRepository;
    @Autowired OpacSearchService opacSearchService;
    @Autowired BookCollectionRepository bookCollectionRepository;
    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired RecordsController recordsController; // Avoid this
    @Autowired BookCoverService bookCoverService;
    private Logger log = LoggerFactory.getLogger(BookCommonService.class);

    public BookCommon saveModifyBookCommon(BookCommon bookCommon) {
        if (bookCommon == null) return null;
        if (bookCommon.get_id() == null && bookCommon.getUid() == null) {
            bookCommon.setUid(bookCommonRepository.generateBookUID());
        } else if (bookCommon.get_id() == null && bookCommon.getUid() != null) {
            BookCommon bookCommon1 = bookCommonRepository.findByUid(bookCommon.getUid());
            if (bookCommon.isUseBookCommonUid() && !bookCommon1.isUseBookCommonUid()) {
                bookCommon.setUid(bookCommonRepository.generateBookUID());
                String imgUrl = bookCoverService.copyImage(bookCommon1.getUid(), bookCommon.getUid());
                if (imgUrl != null) {
                    bookCommon.setImageUrl(imgUrl);
                } else {
                    return null;
                }
            } else {
                bookCommon.set_id(bookCommon1.get_id());
                bookCommon.setImageUrl(bookCommon1.getImageUrl());
            }
        }
        bookCommon = bookCommonRepository.save(bookCommon);
        Record record = recordsRepository.findById(bookCommon.getRecord_id()).get();
            if ((bookCommon.getIsbn()==null && bookCommon.getIssn() ==null) || bookCommon.isUseBookCommonUid()) {
                Subfield subfield = record.getSubfield("856b");
                if (subfield == null) {
                    Field field = new Field("856");
                    field.add(new Subfield('b' , String.valueOf(bookCommon.getUid())));
                    record.add(field);
                } else {
                    subfield.setContent(String.valueOf(bookCommon.getUid()));
                }
            }
//            else {
//
//                Subfield subfield = record.getSubfield("856b");
//                if (subfield != null) {
//                   List<Field> f856List = record.getFields("856");
//                   for(Field f: f856List){
//                       f.removeSubfield('b');
//                   }
//                    record.setCommonBookUid(null);
//                } else {
//                    record.setCommonBookUid(bookCommon.getUid());
//                }
//            }
            record.setCommonBookUid(bookCommon.getUid());
            recordsRepository.save(record);
        return bookCommon;
    }

    public BookCommon getBookCommonByUID(Integer bookCommonUID) {
        return bookCommonRepository.findByUid(bookCommonUID);
    }

    public List<Book> getBooksByRecordIds(List<String> recordsIds) {
        List<Book> books = new ArrayList<>();
        for (String recordId: recordsIds) {
            Optional<Record> r = recordsRepository.findById(recordId);
            if (!r.isPresent()) continue;
            Book b = opacSearchService.getBookByRec(r.get());
            books.add(b);
        }
        return books;
    }

    public List<Book> getBooksByCollectionId(String collectionId) {
        List<Book> retVal = new ArrayList<>();
        if (collectionId == null) return retVal;
        Optional<BookCollection> bc = bookCollectionRepository.findById(collectionId);
        if (!bc.isPresent()) return retVal;
        retVal = getBooksByRecordIds(bc.get().getRecordsIds());
        return retVal;
    }

    public List<Book> getFullBooksByCollectionId(String collectionId) {
        List<Book> retVal = new ArrayList<>();
        if (collectionId == null) return retVal;
        Optional<BookCollection> bc = bookCollectionRepository.findById(collectionId);
        if (!bc.isPresent()) return retVal;
        List<String> recordIds = bc.get().getRecordsIds();
        for (String recordId : recordIds){
            Optional<Record> record = recordsRepository.findById(recordId);
            if (record.isPresent()) {
                retVal.add(opacSearchService.getFullBookByIdMobile(record.get()));
            }
        }
        return retVal;
    }


    private List<Record> searchRecords(SearchModel query) {
        ResponseEntity<List<Record>> responseRecords =
                recordsController.search(query, null, null);
        if (!responseRecords.getStatusCode().equals(HttpStatus.OK)
                || responseRecords.getBody() == null || responseRecords.getBody().size() == 0) return null;
        return responseRecords.getBody();
    }


}
