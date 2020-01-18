package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.controller.RecordsController;
import com.ftninformatika.bisis.rest_service.controller.opac2.BookCommonController;
import com.ftninformatika.bisis.rest_service.controller.opac2.BookCoverController;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:config.properties")
class RecordsPair {

    private static Logger log = Logger.getLogger(RecordsPair.class);
    static String[] LIB_PREFIXES = {"bgb", "gbns", "bs", "msk", "bmb"};

    private BookCommonController bookCommonController;
    private BookCoverController bookCoverController;
    private RecordsController recordsController;
    private RecordsRepository recordsRepository;
    private LibraryConfigurationRepository libraryConfigurationRepository;

    boolean pairBookCommon(BookCommon bookCommon) {
        return pairBookCommonWithSelectedLib(bookCommon, LIB_PREFIXES);
    }

    boolean pairBookCommonWithSelectedLib(BookCommon bookCommon, String[] libPrefixes) {
        if (libPrefixes != null && libPrefixes.length == 1 && libPrefixes[0].equals("all")) {
            libPrefixes = LIB_PREFIXES;
        }
        boolean isPaired = false;
        List<String> isbnPairs = generateIsbnPair(bookCommon.getIsbn());
        if (isbnPairs == null || libPrefixes == null) {
            log.warn("ISBN invalid: " + (bookCommon.getIsbn() == null ? "null" : bookCommon.getIsbn()));
            System.out.println("ISBN invalid: " + (bookCommon.getIsbn() == null ? "null" : bookCommon.getIsbn()));
            return false;
        }
//        TODO: check if passed LibPrefixes exists in config
        for (String libPref: libPrefixes) {
            LibraryPrefixProvider.setPrefix(libPref);
            for (String isbn: isbnPairs) {
                SearchModel query = generateIsbnSearchModel(isbn);
                List<Record> records = searchRecords(query);
                if (records == null) {
                    log.info("No records in library: " + libPref + " for ISBN: " + isbn);
                    System.out.println("No records in library: " + libPref + " for ISBN: " + isbn);
                    continue;
                }
                mergeCommonBookUID(records, libPref, bookCommon.getUid());
                isPaired = true;
            }
        }
        return isPaired;
    }

    private void mergeCommonBookUID(List<Record> records, String libPref, int bcId) {
        for (Record r: records) {
            if (r.getCommonBookUid() != null) {
                log.info("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
                System.out.println("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
                continue;
            }
            r.setCommonBookUid(bcId);
            recordsRepository.save(r);
            log.info("Paired bookCommonUID: " + bcId + " with (" + libPref + ") RN: " + r.getRN());
            System.out.println("Paired bookCommonUID: " + bcId + " with (" + libPref + ") RN: " + r.getRN());
        }
    }

    private void mergeCommonBookUID(List<Record> records, String libPref) {
        for (Record r: records) {
            if (r.getCommonBookUid() != null) {
                log.info("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
                System.out.println("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
                continue;
            }
            r.setCommonBookUid(BooksCommonMergerUtils.UID_COUNTER);
            recordsRepository.save(r);
            log.info("Paired bookCommonUID: " + BooksCommonMergerUtils.UID_COUNTER + " with (" + libPref + ") RN: " + r.getRN());
            System.out.println("Paired bookCommonUID: " + BooksCommonMergerUtils.UID_COUNTER + " with (" + libPref + ") RN: " + r.getRN());
        }
    }

    private List<Record> searchRecords(SearchModel query) {
        ResponseEntity<List<Record>> responseRecords =
                recordsController.search(query, null, null);
        if (!responseRecords.getStatusCode().equals(HttpStatus.OK)
        || responseRecords.getBody() == null || responseRecords.getBody().size() == 0) return null;
        return responseRecords.getBody();
    }

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
