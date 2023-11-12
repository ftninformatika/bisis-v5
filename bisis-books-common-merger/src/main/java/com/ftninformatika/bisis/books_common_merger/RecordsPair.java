package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
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
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:config.properties")
class RecordsPair {

    private static Logger log = Logger.getLogger(RecordsPair.class);

    private BookCommonController bookCommonController;
    private BookCoverController bookCoverController;
    private RecordsController recordsController;
    private RecordsRepository recordsRepository;
    private LibraryConfigurationRepository libraryConfigurationRepository;

    boolean pairBookCommon(BookCommon bookCommon) {
        List<String> libs = new ArrayList<>();
        libs.add("all");
        return pairBookCommonWithSelectedLib(bookCommon, libs);
    }

    boolean pairBookCommonWithSelectedLib(BookCommon bookCommon, List<String> libPrefixes) {
        if (libPrefixes != null && libPrefixes.size() == 1 && libPrefixes.get(0).equals("all")) {
            libPrefixes = libraryConfigurationRepository.findAll().stream()
                    .map(LibraryConfiguration::getLibraryName)
                    .collect(Collectors.toList());
        }
        boolean isPaired = false;
        List<String> isbnPairs = BookCommonHelper.generateIsbnPair(bookCommon.getIsbn());
        List<String> issnPairs = BookCommonHelper.generateIsbnPair(bookCommon.getIssn());
        List<String> isbnOrissn = null;
        if (isbnPairs != null){
            isbnOrissn = isbnPairs;
        }else if(issnPairs != null){
            isbnOrissn = issnPairs;
        }
        for (String libPref: libPrefixes) {
            if (libraryConfigurationRepository.getByLibraryName(libPref) == null){
                continue;
            }
            LibraryPrefixProvider.setPrefix(libPref);
            if (isbnOrissn != null) {
                for (String id : isbnOrissn) {
                    SearchModel query;
                    if (isbnPairs != null) {
                        query = BookCommonHelper.generateSearchModel("BN", id);
                    } else {
                        query = BookCommonHelper.generateSearchModel("SN", id);
                    }
                    List<Record> records = searchRecords(query);
                    if (records == null) {
                        //log.info("No records in library: " + libPref + " for ISBN or ISSN: " + id);
                        continue;
                    }
                    List<Record> toRemove = new ArrayList<>();
                    for (Record r : records) {
                        if (!BookCommonHelper.isValidRecord(r, id)) {
                            toRemove.add(r);
                        }
                    }
                    if (toRemove.size() > 0) {
                        records.removeAll(toRemove);
                        //log.info("Remove " + toRemove.size() + " records");
                        //System.out.println("Remove " + toRemove.size() + " records");
                    }
                    if (records.size() == 0) {
                        continue;
                    }
                    mergeCommonBookUID(records, libPref, bookCommon.getUid());
                    isPaired = true;
                }
            }else{
                SearchModel query = BookCommonHelper.generateSearchModel("856b",String.valueOf(bookCommon.getUid()));
                List<Record> records = searchRecords(query);
                if (records == null) {
                    //log.info("No records in library: " + libPref + " for 856b: " + bookCommon.getUid());
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
                //log.info("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
                //System.out.println("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
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
                //log.info("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
                //System.out.println("Record (" + libPref + ") with RN: " + r.getRN() + " has already paired!");
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
