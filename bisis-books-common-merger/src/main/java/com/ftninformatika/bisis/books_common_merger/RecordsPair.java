package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac.books.BookCommon;
import com.ftninformatika.bisis.opac.controller.BookCommonController;
import com.ftninformatika.bisis.opac.controller.BookCoverController;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.utils.BookCommonHelper;
import com.ftninformatika.utils.LibraryPrefixProvider;
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
            // TODO all libs
        }
        boolean isPaired = false;
        List<String> isbnPairs = BookCommonHelper.generateIsbnPair(bookCommon.getIsbn());
        if (isbnPairs == null || libPrefixes == null) {
            log.warn("ISBN invalid: " + (bookCommon.getIsbn() == null ? "null" : bookCommon.getIsbn()));
            System.out.println("ISBN invalid: " + (bookCommon.getIsbn() == null ? "null" : bookCommon.getIsbn()));
            return false;
        }
//        TODO: check if passed LibPrefixes exists in config
        for (String libPref: libPrefixes) {
            LibraryPrefixProvider.setPrefix(libPref);
            for (String isbn: isbnPairs) {
                // TODO
                SearchModel query = BookCommonHelper.generateIsbnSearchModel(isbn);
                List<Record> records = searchRecords(query);
                if (records == null) {
                    log.info("No records in library: " + libPref + " for ISBN: " + isbn);
                    System.out.println("No records in library: " + libPref + " for ISBN: " + isbn);
                    continue;
                }
                List<Record> toRemove = new ArrayList<>();
                for (Record r: records) {
                    // TODO
                    if (!BookCommonHelper.checkIf1st010FieldisIsbn(r, isbn)) {
                        toRemove.add(r);
                    }
                }
                if (toRemove.size() > 0) {
                    records.removeAll(toRemove);
                    log.info("Remove "+ toRemove.size() + " records");
                    System.out.println("Remove "+ toRemove.size() + " records");
                }
                if (records.size() == 0) {
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
}
