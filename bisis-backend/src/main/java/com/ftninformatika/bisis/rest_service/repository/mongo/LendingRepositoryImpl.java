package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.coders.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dboberic on 17/11/2017.
 */
public class  LendingRepositoryImpl implements LendingRepositoryCustom{
    @Autowired
    MongoTemplate mongoTemplate;

    public List<String> getLendingsCtlgNo(Date startL, Date endL, Date startR, Date endR, String location){
         Query q = new Query();
         Criteria landDate,resumeDate,returnDate,or,and;
         if (startL!=null&&startR!=null) {
             landDate = Criteria.where("lendDate").gte(startL).lte(endL);
             resumeDate = Criteria.where("resumeDate").gte(startL).lte(endL);
             or = new Criteria();
             or.orOperator(landDate, resumeDate);
             returnDate = Criteria.where("returnDate").gte(startR).lte(endR);
             and = new Criteria();
             if (location!=null  ) {
                 q.addCriteria(and.andOperator(or, returnDate, Criteria.where("location").is(location)));
             }else{
                 q.addCriteria(and.andOperator(or, returnDate));
             }
         }else if (startL!=null&&startR==null) {
             landDate = Criteria.where("lendDate").gte(startL).lte(endL);
             resumeDate = Criteria.where("resumeDate").gte(startL).lte(endL);
             or = new Criteria();
             or.orOperator(landDate, resumeDate);
             and = new Criteria();
             if (location!=null  ) {
                 q.addCriteria(and.andOperator(or, Criteria.where("location").is(location)));
             }else{
                 q.addCriteria(or);
             }
         }else if (startL==null&&startR!=null) {
             returnDate = Criteria.where("returnDate").gte(startR).lte(endR);
             if (location!=null  ) {
                 and = new Criteria();
                 q.addCriteria(and.andOperator(returnDate, Criteria.where("location").is(location)));
             }else{
                 q.addCriteria(returnDate);
             }
         }
            System.out.println(q.toString());
         List<Lending> l=mongoTemplate.find(q,Lending.class);
         return l.stream().map(i->i.getCtlgNo()).collect(Collectors.toList());

    }

}
