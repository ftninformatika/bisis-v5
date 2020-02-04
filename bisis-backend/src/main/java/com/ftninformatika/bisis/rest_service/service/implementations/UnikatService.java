package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<String> allLibs = libraryConfigurationRepository.findAll().stream()
                .map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());

        // Search al indexes once
        String indicesRequestString = "_all";

        // Search selected libs at once
        if(unikatSearchRequest.getSelectedLibs().size() > 0)
            indicesRequestString =  String.join("library_domain,", unikatSearchRequest.getSelectedLibs()) + "library_domain";

        int page = 0;
        int pSize = 10;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;

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

        ii.forEach(
                elasticPrefixEntity -> {
                    String recMongoId = elasticPrefixEntity.getId();
                    try {
                        String lib = elasticPrefixEntity.getPrefixes().get("libName").get(0);
                        Optional<Record> r = recordsRepository.findById(recMongoId);
                        if (!r.isPresent()) {
                            System.out.println("Error getting record: " + recMongoId);
                        } else {
                            UnikatBookRef ub = new UnikatBookRef(opacSearchService.getBookByRec(r.get()), lib);
                            retVal.add(ub);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Exception getting record: " + recMongoId);
                    }
                }
        );

        return new PageImpl(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }
}
