package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.unikat.UnikatBook;
import com.ftninformatika.bisis.unikat.UnikatBookRef;
import com.ftninformatika.bisis.unikat.UnikatSearchRequest;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
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

    @Autowired RecordsRepository recordsRepository;
    @Autowired LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    @Autowired OpacSearchService opacSearchService;

    public PageImpl<List<UnikatBookRef>> unkatSearch(UnikatSearchRequest unikatSearchRequest, Integer pageNumber, Integer pageSize) {
        List<UnikatBookRef> retVal = new ArrayList<>();

        if (unikatSearchRequest == null || unikatSearchRequest.getSearchModel() == null)
            return null;

        String[] indicesRequestString = null;
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

        Pageable p = new PageRequest(page, pSize);

        BoolQueryBuilder query = ElasticUtility.makeQuery(unikatSearchRequest.getSearchModel());

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withIndices(indicesRequestString)
                .withTypes("record")
                .withPageable(p)
                // Sort by ISBN for easier merging
                .withSort(SortBuilders.fieldSort("prefixes.BN"));

        Iterable<ElasticPrefixEntity> ii = elasticsearchTemplate.queryForPage(searchQuery.build(), ElasticPrefixEntity.class);

//        ii.forEach(
//                elasticPrefixEntity -> {
//                    String recMongoId = elasticPrefixEntity.getId();
//                    try {
//                        String lib = elasticPrefixEntity.getPrefixes().get("libName").get(0);
//                        LibraryPrefixProvider.setPrefix(lib);
//                        Optional<Record> r = recordsRepository.findById(recMongoId);
//                        if (!r.isPresent()) {
//                            System.out.println("Error getting record: " + recMongoId);
//                        } else {
//                            Book b = opacSearchService.getBookByRec(r.get());
//                            UnikatBookRef ub = new UnikatBookRef(recMongoId, lib);
//                            retVal.add(ub);
//                        }
//                    }
//                    catch (Exception e) {
//                        System.out.println("Exception getting record: " + recMongoId);
//                    }
//                }
//        );
        retVal = mergeResults(ii);

        return new PageImpl(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }

    private List<UnikatBookRef> mergeResults(Iterable<ElasticPrefixEntity> esResults) {
        List<UnikatBook> retVal = new ArrayList<>();
        Map<String, List<UnikatBookRef>> resMap = new HashMap<>();
        esResults.forEach(
                e -> {
                    String record_id = e.getId();
                    String isbn = null;
                    try {
                        isbn = e.getPrefixes().get("BN").get(0);
                    }
                    catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }
                    String library = e.getPrefixes().get("libName").get(0);
                    List<UnikatBookRef> mapVal = resMap.get(isbn);
                    if (mapVal == null) {
                        mapVal = new ArrayList<>();
                        mapVal.add(new UnikatBookRef(record_id, library));
                        resMap.put(isbn, mapVal);
                    }
                    else {
                        resMap.get(isbn).add(new UnikatBookRef(record_id, library));
                    }
                }
        );

//        TODO: make UnikatBooks from map entries
        return null;
    }
}
