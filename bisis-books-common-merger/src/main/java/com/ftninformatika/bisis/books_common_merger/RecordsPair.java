package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.books.BookCommon;
import com.ftninformatika.bisis.opac.controller.BookCommonController;
import com.ftninformatika.bisis.opac.controller.BookCoverController;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.utils.BookCommonHelper;
import com.ftninformatika.utils.LibraryPrefixProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private ElasticRecordsRepository elasticRecordsRepository;

    private LibraryConfigurationRepository libraryConfigurationRepository;

    void pairBookCommon(Iterable<BookCommon> bookCommon) {
        List<String> libs = new ArrayList<>();
        libs.add("all");
        pairBookCommonWithSelectedLib(bookCommon, libs);
    }

    void pairBookCommonWithSelectedLib(Iterable<BookCommon> booksCommon, List<String> libPrefixes) {
        if (libPrefixes != null && libPrefixes.size() == 1 && libPrefixes.get(0).equals("all")) {
            libPrefixes = libraryConfigurationRepository.findAll().stream()
                    .map(LibraryConfiguration::getLibraryName)
                    .collect(Collectors.toList());
        }
        boolean isPaired = false;
        List<String> isbnList= new ArrayList<>();
        List<String> issnList = new ArrayList<>();
        List<Integer> bookUIDs = new ArrayList<>();
        for(BookCommon bc: booksCommon){
            isbnList.addAll(BookCommonHelper.generateIsbnPair(bc.getIsbn()));
            issnList.addAll(BookCommonHelper.generateIsbnPair(bc.getIssn()));
            bookUIDs.add(bc.getUid());
        }
        for (String libPref: libPrefixes) {
            if (libraryConfigurationRepository.getByLibraryName(libPref) == null){
                continue;
            }
            LibraryPrefixProvider.setPrefix(libPref);
            // TODO napravi upit sa IN operatorom
            //Trebaju mi zapisi takvi da se BN nalazi u listi isbnList ili SN u listi issnList ili 856b u listi bookUIDs
            BoolQueryBuilder queryBuilder =  QueryBuilders.boolQuery();
            queryBuilder.should(QueryBuilders.termsQuery("BN", isbnList));
            queryBuilder.should(QueryBuilders.termsQuery("SN", issnList));
            queryBuilder.should(QueryBuilders.termsQuery("856b", bookUIDs));

            Iterable<ElasticPrefixEntity> records = elasticRecordsRepository.search(queryBuilder);
            if (records == null) {
                continue;
            }
            List<Record> recordsForRemove = new ArrayList<>();
            booksCommon.forEach(bc ->{
                List<Record> recordsFiltered = StreamSupport.stream(records.spliterator(), false).
                        filter(r-> r.getPrefixes().get("BN").contains(bc.getIsbn()) ||
                        r.getPrefixes().get("SN").contains(bc.getIssn()) ||
                        r.getPrefixes().get("856b").contains(bc.getUid())).
                        map(r->recordsRepository.getByID(Integer.parseInt(r.getId()))).collect(Collectors.toList());

                recordsFiltered.forEach( rec ->{
                    if (!BookCommonHelper.isValidRecord(rec, bc.getIsbn()) || !BookCommonHelper.isValidRecord(rec, bc.getIssn())) {
                        recordsForRemove.add(rec);
                    }
                });
                if (recordsForRemove.size() > 0) {
                    recordsFiltered.removeAll(recordsForRemove);
                }
                if (recordsFiltered.size() != 0) {
                    mergeCommonBookUID(recordsFiltered, libPref, bc.getUid());
                    log.info("Paired book common " + bc.getUid() + " for lib: " + libPref);
                }
            });
        }
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
