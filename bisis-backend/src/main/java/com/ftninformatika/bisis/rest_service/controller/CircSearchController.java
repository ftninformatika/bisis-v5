package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.search.SearchModelCirc;
import com.ftninformatika.bisis.search.SearchModelMember;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by dboberic on 17/11/2017.
 */
@RestController
@RequestMapping("/search")
public class CircSearchController {

    @Autowired LendingRepository lendingRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;

    @RequestMapping(value = "/circ/recordIds", method = RequestMethod.POST )
    public List<String> searchCircRecordIds(@RequestBody SearchModelCirc search) {
        BoolQueryBuilder query= ElasticUtility.makeQuery(search);
        if (search.getStartDateLend()!=null || search.getStartDateRet() != null) {
            List<String> ctlgNos=lendingRepository.getLendingsCtlgNo(search.getStartDateLend(),search.getEndDateLend(),search.getStartDateRet(),search.getEndDateRet(),search.getLocation());
            query =query.filter(QueryBuilders.termsQuery("prefixes.IN", ctlgNos));
        }
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(query);
        return ElasticUtility.getIdsFromElasticIterable(ii);
    }

    @RequestMapping(value = "/circ/members", method = RequestMethod.POST )
    public List<Member> searchCircMembers(@RequestBody SearchModelMember search) {

        String location = null;
        Date lendDateStart = null, lendDateEnd = null, returnDateStart = null,
             returnDateEnd = null, deadlineStart = null, deadlineEnd = null;

        Object [] lenddates = (Object [])search.getValueForPrefix("lendDate");
        Object [] returndates = (Object [])search.getValueForPrefix("returnDate");
        Object [] deadLinedates = (Object [])search.getValueForPrefix("deadline");

        if(lenddates != null) {
            lendDateStart = (Date)(lenddates)[0];
            lendDateEnd = (Date)(lenddates)[1];
            location = (String)(lenddates)[2];
        }

        if(returndates != null) {
            returnDateStart = (Date)(returndates)[0];
            returnDateEnd = (Date)(returndates)[1];
            location = (String)(returndates)[2];
        }
        if(deadLinedates != null) {
            deadlineStart = (Date)(deadLinedates)[0];
            deadlineEnd = (Date)(deadLinedates)[1];
            location = (String)(deadLinedates)[2];
        }

        List userIds = lendingRepository.getLendingsUserId(wildcardHandle((String)search.getValueForPrefix("ctlgNo")),
                    wildcardHandle((String)search.getValueForPrefix("librarianLend")),
                    wildcardHandle((String)search.getValueForPrefix("librarianReturn")),location,
                    lendDateStart, lendDateEnd, returnDateStart, returnDateEnd, deadlineStart, deadlineEnd);

        return  memberRepository.getMembersFilteredByLending(search,userIds);

    }

    private String wildcardHandle(String text){
        if (text == null) {
            return text;
        } else if(!text.startsWith("*")) {
            text = "^"+text;
        } else if (!text.endsWith("*")) {
            text = text+"$";
        }
        text = text.replace("*","");
        return text;
    }

}
