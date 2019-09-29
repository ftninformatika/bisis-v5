package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.controller.RecordsController;
import com.ftninformatika.bisis.rest_service.controller.opac2.BookCommonController;
import com.ftninformatika.bisis.rest_service.controller.opac2.BookCoverController;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class RecordsPair {

    private static Logger log = Logger.getLogger(RecordsPair.class);
    static String[] LIB_PREFIXES = {"bgb", "gbns", "bs", "msk"};

    private BookCommonController bookCommonController;
    private BookCoverController bookCoverController;
    private RecordsController recordsController;
    private RecordsRepository recordsRepository;

    boolean pairBookCommon(BookCommon bookCommon) {
        boolean isPaired = false;
        String[] isbnPairs = generateIsbnPair(bookCommon.getIsbn());
        if (isbnPairs == null) {
            log.warn("ISBN invalid: " + (bookCommon.getIsbn() == null ? "null" : bookCommon.getIsbn()));
            System.out.println("ISBN invalid: " + (bookCommon.getIsbn() == null ? "null" : bookCommon.getIsbn()));
            return false;
        }
        for (String libPref: LIB_PREFIXES) {
            LibraryPrefixProvider.setPrefix(libPref);
            for (String isbn: isbnPairs) {
                if (isbn == null) continue;
                SearchModel query = generateIsbnSearchModel(isbn);
                List<Record> records = searchRecords(query);
                if (records == null) {
                    log.info("No records in library: " + libPref + " for ISBN: " + isbn);
                    System.out.println("No records in library: " + libPref + " for ISBN: " + isbn);
                    continue;
                }
                mergeCommonBookUID(records, libPref);
                isPaired = true;
            }
        }
        return isPaired;
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
    private String[] generateIsbnPair(String isbn) {
        String[] isbnPair = new String[2];
        if (!validateIsbn(isbn)) return null;
        isbnPair[0] = isbn;
        String isbnSecFormat;
        if (!validateIsbn10(isbn)) {
            isbnSecFormat = isbn.substring(3);
            if (validateIsbn10(isbnSecFormat)) {
                isbnPair[1] = isbnSecFormat;
                return isbnPair;
            }
        }
        if (!validateIsbn13(isbn)) {
            isbnSecFormat = 978 + isbn;
            if (validateIsbn13(isbnSecFormat)) {
                isbnPair[1] = isbnSecFormat;
                return isbnPair;
            }
        }
        return isbnPair;
    }

    private boolean validateIsbn(String isbn) {
        return  validateIsbn10(isbn) || validateIsbn13(isbn);
    }

    private boolean validateIsbn10(String isbn) {
        if ( isbn == null ) {
            return false;
        }
        //remove any hyphens
        isbn = isbn.replaceAll( "-", "" ).trim();
        //must be a 10 digit ISBN
        if ( isbn.length() != 10 ) {
            return false;
        }
        try {
            int tot = 0;
            for ( int i = 0; i < 9; i++ ) {
                int digit = Integer.parseInt( isbn.substring( i, i + 1 ) );
                tot += ((10 - i) * digit);
            }
            String checksum = Integer.toString( (11 - (tot % 11)) % 11 );
            if ( "10".equals( checksum ) ) {
                checksum = "X";
            }
            return checksum.equals( isbn.substring( 9 ) );
        }
        catch ( NumberFormatException nfe ) {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }

    private boolean validateIsbn13(String isbn) {
        if ( isbn == null ) {
            return false;
        }
        //remove any hyphens
        isbn = isbn.replaceAll( "-", "" ).trim();
        //must be a 13 digit ISBN
        if (isbn.length() != 13) {
            return false;
        }
        try {
            int tot = 0;
            for ( int i = 0; i < 12; i++ ) {
                int digit = Integer.parseInt( isbn.substring( i, i + 1 ) );
                tot += (i % 2 == 0) ? digit : digit * 3;
            }
            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = 10 - (tot % 10);
            if ( checksum == 10 ) {
                checksum = 0;
            }
            return checksum == Integer.parseInt( isbn.substring( 12 ) );
        }
        catch (NumberFormatException nfe) {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }
}
