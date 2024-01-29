package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.unikat.UnikatBook;
import com.ftninformatika.bisis.unikat.UnikatBookRef;
import com.ftninformatika.bisis.unikat.UnikatSearchRequest;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.LibraryPrefixProvider;
import com.ftninformatika.utils.RecordUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnikatService {

    @Autowired
    RecordsRepository recordsRepository;
    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    @Autowired OpacSearchService opacSearchService;
    Logger logger = LoggerFactory.getLogger(UnikatService.class);

    public PageImpl<List<UnikatBook>> unkatSearch(UnikatSearchRequest unikatSearchRequest, Integer pageNumber, Integer pageSize) {
        List<UnikatBook> retVal;

        if (unikatSearchRequest == null || unikatSearchRequest.getSearchModel() == null)
            return null;

        String[] indicesRequestString;
        // Search selected libs at once
        if(unikatSearchRequest.getSelectedLibs().size() > 0) {
            indicesRequestString = new String[unikatSearchRequest.getSelectedLibs().size()];
            for (int i = 0; i < unikatSearchRequest.getSelectedLibs().size(); i++) {
                indicesRequestString[i] = unikatSearchRequest.getSelectedLibs().get(i) + "library_domain";
            }
        }
        else {
            indicesRequestString = new String[]{"_all"};
        }

        int page = 0;
        int pSize = 100;

        if (pageNumber != null)
            page = pageNumber;
//        if (pageSize != null)
//            pSize = pageSize;

        Pageable p = PageRequest.of(page, pSize);

        BoolQueryBuilder query = ElasticUtility.makeQuery(unikatSearchRequest.getSearchModel());

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withIndices(indicesRequestString)
                .withTypes("record")
                .withPageable(p)
                // Sort by ISBN for easier merging
                .withSort(SortBuilders.fieldSort("prefixes.BN"));

        Iterable<ElasticPrefixEntity> ii = elasticsearchTemplate.queryForPage(searchQuery.build(), ElasticPrefixEntity.class);
        retVal = mergeResults(ii);
        retVal = retVal.stream().sorted(Comparator.comparing(ub -> ub.getBook().getPublishYear(), Comparator.reverseOrder())).collect(Collectors.toList());

        return new PageImpl(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }

    private List<UnikatBook> mergeResults(Iterable<ElasticPrefixEntity> esResults) {
        Map<String, String> libNamesMap = libraryConfigurationRepository.findAll().stream()
                .filter(lc -> lc.getLibraryName() != null && lc.getLibraryFullName() != null)
                .collect(Collectors.toMap(LibraryConfiguration::getLibraryName, LibraryConfiguration::getLibraryFullName));
        List<UnikatBook> retVal = new ArrayList<>();
        Map<String, List<UnikatBookRef>> resMap = new HashMap<>();
        for (ElasticPrefixEntity e : esResults) {
            String record_id = e.getId();
            String isbn;
            String library;
            try {
                isbn = e.getPrefixes().get("BN").get(0);
                library = e.getPrefixes().get("libName").get(0);
            } catch (NullPointerException ne) {
                //logger.error(ne.getMessage());
                continue;
            }
            List<String> isbnPair = RecordUtils.generateIsbnPair(isbn);
            if (isbnPair == null || isbnPair.size() == 0 && isbn != null)
                isbnPair = Arrays.asList(isbn);

            String isbn0 = isbnPair.get(0);
            String isbn1 = isbnPair.size() == 2 ? isbnPair.get(1) : null;
            List<UnikatBookRef> mapVal0 = resMap.get(isbn0);
            List<UnikatBookRef> mapVal1 = resMap.get(isbn1);
            if (mapVal0 == null && mapVal1 == null) {
                List<UnikatBookRef> mapVal = new ArrayList<>();
                mapVal.add(new UnikatBookRef(record_id, library, libNamesMap.get(library)));
                resMap.put(isbn0, mapVal);
            } else if (mapVal0 != null && mapVal1 == null) {
                if (!alreadyExist(mapVal0, mapVal1, library))
                    resMap.get(isbn0).add(new UnikatBookRef(record_id, library, libNamesMap.get(library)));
            } else {
                if (!alreadyExist(mapVal0, mapVal1, library))
                    resMap.get(isbn1).add(new UnikatBookRef(record_id, library, libNamesMap.get(library)));
            }
        }

        for (String key: resMap.keySet()) {
            List<UnikatBookRef> bookRefs = resMap.get(key);
            LibraryPrefixProvider.setPrefix(bookRefs.get(0).getLib());
            Record r = recordsRepository.findById(bookRefs.get(0).get_id()).get();
            Book b = opacSearchService.getBookByRec(r);
            retVal.add(new UnikatBook(b, bookRefs));
        }
        return retVal;
    }

    private boolean alreadyExist(List<UnikatBookRef> refList0, List<UnikatBookRef> refList1, String lib ) {
        boolean contains0 = (refList0 != null && refList0.stream().anyMatch(i -> i.getLib().equals(lib)));
        boolean contains1 = (refList1 != null && refList1.stream().anyMatch(i -> i.getLib().equals(lib)));
        return contains0 || contains1;
    }
}
