package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 6/28/2017.
 */
@RestController
@RequestMapping("/expand_prefix_controller")
public class ExpandPrefixController {

    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    ElasticRecordsRepository elasticsearchRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getExpandPrefix(@Param("prefix") String prefix, @Param("text") String text) {

        ArrayList<String> retVal = new ArrayList<String>();

        Iterable<ElasticPrefixEntity> ii = elasticsearchRepository.search(ElasticUtility.makeExpandQuery(prefix,text));

        for (ElasticPrefixEntity ep: ii) {
            for (String ss: ep.getPrefixes().get(prefix)) {
//            if (!retVal.contains(ep.getPrefixes().get(prefix)))
//                retVal.add(ep.getPrefixes().get(prefix));
                if (!retVal.contains(ss))
                    retVal.add(ss);
            }
        }
        return new ResponseEntity<List<String>>(retVal, HttpStatus.OK);
    }



}