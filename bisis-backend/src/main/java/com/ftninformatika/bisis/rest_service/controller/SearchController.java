package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.SearchModelCirc;
import com.ftninformatika.bisis.search.SearchModelMember;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dboberic on 17/11/2017.
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    LendingRepository lendingRepository;
    @Autowired
    ElasticRecordsRepository elasticRecordsRepository;

    @RequestMapping(value = "/circ/recordIds", method = RequestMethod.POST )
    public List<String> searchCircRecordIds(@RequestBody SearchModelCirc search){
        BoolQueryBuilder query= ElasticUtility.makeQuery(search);
        if (search.getStartDateLend()!=null || search.getStartDateRet()!=null){

            List<String> ctlgNos=lendingRepository.getLendingsCtlgNo(search.getStartDateLend(),search.getEndDateLend(),search.getStartDateRet(),search.getEndDateRet(),search.getLocation());
            query =query.filter(QueryBuilders.termsQuery("prefixes.IN", ctlgNos));
        }
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(query);
        return ElasticUtility.getIdsFromElasticIterable(ii);
        
    }

    @RequestMapping(value = "/circ/members", method = RequestMethod.POST )
    public List<Member> searchCircMembers(@RequestBody SearchModelMember search){
//        BoolQueryBuilder query= ElasticUtility.makeQuery(search);
//        if (search.getStartDateLend()!=null || search.getStartDateRet()!=null){
//
//            List<String> ctlgNos=lendingRepository.getLendingsCtlgNo(search.getStartDateLend(),search.getEndDateLend(),search.getStartDateRet(),search.getEndDateRet(),search.getLocation());
//            query =query.filter(QueryBuilders.termsQuery("prefixes.IN", ctlgNos));
//        }
//        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(query);
//        return ElasticUtility.getIdsFromElasticIterable(ii);
        return null;
    }

}
