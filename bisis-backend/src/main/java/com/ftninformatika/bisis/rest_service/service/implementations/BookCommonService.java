package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.BookCollection;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.books.BookCommon;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.utils.BookCommonHelper;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private Logger log = Logger.getLogger(BookCommonService.class);

//    TODO: refactor this at some point
    public BookCommon saveModifyBookCommon(BookCommon bookCommon) {
        boolean isNew = false;
        if (bookCommon == null) return null;
        if (bookCommon.get_id() == null && bookCommon.getUid() == null) {
            bookCommon.setUid(bookCommonRepository.generateBookUID());
            isNew = true;
        }
        else if (bookCommon.get_id() == null && bookCommon.getUid() != null) {
            BookCommon bookCommon1 = bookCommonRepository.findByUid(bookCommon.getUid());
            if (bookCommon1 != null) {
                bookCommon.set_id(bookCommon1.get_id());
                bookCommon.setImageUrl(bookCommon1.getImageUrl());
            }
        }
        if (bookCommon.getUid() == null || (bookCommon.getIsbn() == null && bookCommon.getIssn() == null))
            return null;
        BookCommon bc = bookCommonRepository.save(bookCommon);
        if (isNew) {
            // TODO
            //update record_id is bookCommon - u 856b dodatu uid
            mergeBookCommonWithRecords(bc);
        }
        return bc;
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

    private void mergeBookCommonWithRecords(BookCommon bookCommon) {
        List<String> libCodes = libraryConfigurationRepository.findAll()
                .stream().map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());
        for (String lib: libCodes) {
            if (bookCommon.getIsbn() != null) {
                List<String> isbnPair = BookCommonHelper.generateIsbnPair(bookCommon.getIsbn());
                if (isbnPair == null) continue;
                LibraryPrefixProvider.setPrefix(lib);
                for (String isbn: isbnPair) {
                    SearchModel query = BookCommonHelper.generateIsbnSearchModel(isbn);
                    List<Record> records = searchRecords(query);
                    if (records == null) {
//                        System.out.println("No records in library: " + lib + " for ISBN: " + isbn);
                        continue;
                    }
                    List<Record> toRemove = new ArrayList<>();
                    for (Record r: records) {
                        if (!BookCommonHelper.checkIf1st010FieldisIsbn(r, isbn)) {
                            toRemove.add(r);
                        }
                    }
                    if (toRemove.size() > 0) {
                        records.removeAll(toRemove);
                        log.info("Remove "+ toRemove.size() + " records");
                        System.out.println("Remove "+ toRemove.size() + " records");
                    }
                    for (Record r : records) {
                        if (r.getCommonBookUid() == null) {
                            r.setCommonBookUid(bookCommon.getUid());
                            recordsRepository.save(r);
                            log.info("Merged book common UID: " + bookCommon.getUid() + " with record RN: " + r.getRN() + " library: " + lib);
                        }
                    }
                }
            }
        }
    }


    private List<Record> searchRecords(SearchModel query) {
        ResponseEntity<List<Record>> responseRecords =
                recordsController.search(query, null, null);
        if (!responseRecords.getStatusCode().equals(HttpStatus.OK)
                || responseRecords.getBody() == null || responseRecords.getBody().size() == 0) return null;
        return responseRecords.getBody();
    }


}
