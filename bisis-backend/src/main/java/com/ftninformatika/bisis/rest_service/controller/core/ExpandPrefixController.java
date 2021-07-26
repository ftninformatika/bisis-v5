package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.CloseableIterator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petar on 6/28/2017.
 */
@RestController
@RequestMapping("/expand_prefix_controller")
public class ExpandPrefixController {

    @Autowired
    RecordsRepository recordsRepository;
    @Autowired ElasticRecordsRepository elasticsearchRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getExpandPrefix(@Param("prefix") String prefix, @Param("text") String text) {
        List<String> retVal = new ArrayList<>();
        String finalText = LatCyrUtils.toLatinUnaccented(text);

        Pageable pageable = PageRequest.of(0, 5000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(ElasticUtility.makeExpandQuery(prefix,text))
                .withPageable(pageable)
                .build();

        CloseableIterator<ElasticPrefixEntity> streamEs = elasticsearchTemplate.stream(searchQuery, ElasticPrefixEntity.class);
        while (streamEs.hasNext()) {
            ElasticPrefixEntity e = streamEs.next();
            retVal.addAll(
                    e.getPrefixes().get(prefix).stream()
                     .filter(p -> p.contains(finalText))
                     .collect(Collectors.toList())
            );
        }
        if (retVal.size() > 0) return new ResponseEntity<>(retVal, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}