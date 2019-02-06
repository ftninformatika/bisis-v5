package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.RangeRegexGenerator;
import com.mongodb.DBCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RecordsRepositoryImpl {

    @Autowired MongoTemplate mongoTemplate;



    public List<Integer> findInvNumHoles(String invFrom, String invTo) {
        List<Integer> retVal = new ArrayList<>();
        RangeRegexGenerator regexGenerator = new RangeRegexGenerator();
        List<String> regexes = regexGenerator.getRegex(invFrom, invTo);


        List<Criteria> regexCr = new ArrayList<>();
        for (String reg: regexes) {
            Pattern p = Pattern.compile(reg);
            regexCr.add(Criteria.where("primerci").elemMatch(Criteria.where("invBroj").regex(p)));
        }
        //Criteria cr = new Criteria().orOperator(regexCr);
        Query q = new Query();
        q.addCriteria(regexCr.get(1));
        //q.fields().include("primerci.invBroj");
        DBCursor cursor = mongoTemplate.getCollection("bgb_records").find(q.getQueryObject());

        return retVal;
    }
}
