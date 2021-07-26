package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.BookCollection;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.search.SearchModel;
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

    private void mergeBookCommonWithRecords(BookCommon bookCommon) {
        List<String> libCodes = libraryConfigurationRepository.findAll()
                .stream().map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());
        for (String lib: libCodes) {
            if (bookCommon.getIsbn() != null) {
                List<String> isbnPair = generateIsbnPair(bookCommon.getIsbn());
                if (isbnPair == null) continue;
                LibraryPrefixProvider.setPrefix(lib);
                for (String isbn: isbnPair) {
                    SearchModel query = generateIsbnSearchModel(isbn);
                    List<Record> records = searchRecords(query);
                    if (records == null) {
//                        System.out.println("No records in library: " + lib + " for ISBN: " + isbn);
                        continue;
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

//    TODO: move this to core ---------------------------------------------]
    private SearchModel generateIsbnSearchModel(String isbn) {
        SearchModel searchModel = new SearchModel();
        searchModel.setPref1("BN");
        searchModel.setText1(isbn);
        searchModel.setOper1("AND");
        searchModel.setPref2("");
        searchModel.setText2("");
        searchModel.setOper2("AND");
        searchModel.setPref3("");
        searchModel.setText3("");
        searchModel.setOper3("AND");
        searchModel.setPref4("");
        searchModel.setText4("");
        searchModel.setOper4("AND");
        searchModel.setPref5("");
        searchModel.setText5("");
        return searchModel;
    }

    /**
     * Neki isbn su uneti u formatu [10] a neki u formatu [13]
     * pretragu vrsimo za obe varijante jer referenciraju isti zapis
     */
    private List<String> generateIsbnPair(String isbn) {
        List<String> isbnPair = new ArrayList<>();
        if (!validateIsbn(isbn)) return null;
        isbnPair.add(isbn);
        String isbnSecFormat;
        if (!validateIsbn10(isbn)) {
            isbnSecFormat = isbn.substring(3).replaceAll("-", "").trim();
            isbnPair.add(isbnSecFormat);
            return isbnPair;
        }
        if (!validateIsbn13(isbn)) {
            isbnSecFormat = 978 + isbn.replaceAll("-", "").trim();
            isbnPair.add(isbnSecFormat);
            return isbnPair;
        }
        return isbnPair;
    }

    private boolean validateIsbn(String isbn) {
        return  validateIsbn10(isbn) || validateIsbn13(isbn);
    }

    private boolean validateIsbn10(String isbn) {
        if (isbn == null) {
            return false;
        }
        isbn = isbn.replaceAll( "-", "" ).trim().replace(" ", "");
        if (isbn.length() != 10) {
            return false;
        }
        try {
            Double.parseDouble(isbn.substring(0, 9));
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean validateIsbn13(String isbn) {
        if ( isbn == null ) {
            return false;
        }
        isbn = isbn.replaceAll( "-", "" ).trim().replace(" ", "");
        if (isbn.length() != 13) {
            return false;
        }
        try {
            Double.parseDouble(isbn.substring(0, 12));
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }

}
