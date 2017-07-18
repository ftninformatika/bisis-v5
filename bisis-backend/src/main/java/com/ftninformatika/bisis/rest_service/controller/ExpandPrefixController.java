package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
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
    ElasticsearchRepository elasticsearchRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getExpandPrefix(@Param("prefix") String prefix, @Param("text") String text) {

        Iterable<ElasticPrefixEntity> ii = elasticsearchRepository.search(makeExpandQuery(prefix,text));



        ArrayList<String> res = new ArrayList<String>();
        /*res.add("izbor1 sa backenda");
        res.add("izbor2 sa backenda");
        res.add("izbor3 sa backenda");
        res.add("izbor4 sa backenda");*/

        for (ElasticPrefixEntity ep: ii)
            res.add(ep.getPrefixes().get(prefix));

        return new ResponseEntity<List<String>>(res, HttpStatus.OK);
    }

    private BoolQueryBuilder makeExpandQuery(String prefName, String prefValue){
        BoolQueryBuilder retVal = new BoolQueryBuilder();

        try{
            if ("".equals(prefName) || "".equals(prefValue))
                return null;

            retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes."+prefName, prefValue));
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        return retVal;
    }

}