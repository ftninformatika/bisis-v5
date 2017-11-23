package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.SearchModelCirc;
import com.ftninformatika.bisis.search.SearchModelMember;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    MemberRepository memberRepository;
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

        //TODO napraviti da nije case sensitive
        //TODO ne radi pretrazivanje po inventarnom broju
        String location;
        Date lendDateStart =null,lendDateEnd =null,returnDateStart =null,returnDateEnd =null,deadlineStart =null,deadlineEnd =null;

        if(search.getLocation1()!=null)
            location=search.getLocation1();
        else{
            location=search.getLocation2();
        }
        List lenddates=(List)search.getValueForPrefix("lendDate");
        List returndates=(List)search.getValueForPrefix("returnDate");
        List deadLinedates=(List)search.getValueForPrefix("deadline");

        if(lenddates!=null){
            lendDateStart=(Date)(lenddates).get(0);
            lendDateEnd=(Date)(lenddates).get(1);
        }

        if(returndates!=null){
            returnDateStart=(Date)(returndates).get(0);
            returnDateEnd=(Date)(returndates).get(1);
        }
        if(deadLinedates!=null){
            deadlineStart=(Date)(deadLinedates).get(0);
            deadlineEnd=(Date)(deadLinedates).get(1);
        }

        List userIds=lendingRepository.getLendingsUserId((String)search.getValueForPrefix("ctlgNo"),
                (String)search.getValueForPrefix("librarianLend"),(String)search.getValueForPrefix("librarianReturn"),location,
                lendDateStart,lendDateEnd,returnDateStart,returnDateEnd,deadlineStart,deadlineEnd);

        return  memberRepository.getMembersFilteredByLending(search,userIds);

    }

}
