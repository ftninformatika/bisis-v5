package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dboberic on 17/11/2017.
 */
public class  LendingRepositoryImpl implements LendingRepositoryCustom{
    @Autowired
    MongoTemplate mongoTemplate;

    private Criteria createCriteria(Date startL, Date endL, Date startR, Date endR, String location){
        Criteria landDate,resumeDate,returnDate,or,and;
        Criteria currentCriteria =null;
        if (startL!=null&&startR!=null) {
            landDate = Criteria.where("lendDate").gte(startL).lte(endL);
            resumeDate = Criteria.where("resumeDate").gte(startL).lte(endL);
            or = new Criteria();
            or.orOperator(landDate, resumeDate);
            returnDate = Criteria.where("returnDate").gte(startR).lte(endR);
            and = new Criteria();
            if (location!=null  ) {
                currentCriteria = and.andOperator(or, returnDate, Criteria.where("location").is(location));
            }else{
                currentCriteria = and.andOperator(or, returnDate);

            }
        }else if (startL!=null&&startR==null) {
            landDate = Criteria.where("lendDate").gte(startL).lte(endL);
            resumeDate = Criteria.where("resumeDate").gte(startL).lte(endL);
            or = new Criteria();
            or.orOperator(landDate, resumeDate);
            and = new Criteria();
            if (location!=null  ) {
                currentCriteria = and.andOperator(or, Criteria.where("location").is(location));
            }else{
                currentCriteria =or;
            }
        }else if (startL==null&&startR!=null) {
            returnDate = Criteria.where("returnDate").gte(startR).lte(endR);
            if (location!=null  ) {
                and = new Criteria();
                currentCriteria = and.andOperator(returnDate, Criteria.where("location").is(location));
            }else{
                currentCriteria = returnDate;
            }
        }
        return currentCriteria;
    }

    public List<String> getLendingsCtlgNo(Date startL, Date endL, Date startR, Date endR, String location){
         Criteria criteria = createCriteria(startL,endL,startR,endR,location);
         if (criteria!=null) {
             Query q = new Query();
             q.addCriteria(criteria);
             List<Lending> l = mongoTemplate.find(q, Lending.class);
             return l.stream().map(i -> i.getCtlgNo()).collect(Collectors.toList());
         }else{
             return null;
         }

    }

    public List<Lending> getLenignsWithAnyActivityOnDate(Date dateOfActivity, String location){
        List<Lending> retVal = null;
        Criteria lendDateCriteria, returnDateCriteria, resumeDateCriteria, or, desiredCriteria;
        lendDateCriteria = Criteria.where("lendDate").gte(DateUtils.getYesterday(DateUtils.getEndOfDay(dateOfActivity))).lte(DateUtils.getEndOfDay(dateOfActivity));
        returnDateCriteria = Criteria.where("returnDate").gte(DateUtils.getYesterday(DateUtils.getEndOfDay(dateOfActivity))).lte(DateUtils.getEndOfDay(dateOfActivity));
        resumeDateCriteria = Criteria.where("resumeDate").gte(DateUtils.getYesterday(DateUtils.getEndOfDay(dateOfActivity))).lte(DateUtils.getEndOfDay(dateOfActivity));
        or = new Criteria();
        or.orOperator(lendDateCriteria, returnDateCriteria, resumeDateCriteria);

        if(location != null && !location.equals(""))
            desiredCriteria = or.andOperator(or, Criteria.where("location").is(location));
        else
            desiredCriteria = or;

        if (desiredCriteria != null){
            Query q = new Query();
            q.addCriteria(desiredCriteria);
            q.with(new Sort(Sort.Direction.DESC, "lendDate"));
            q.with(new Sort(Sort.Direction.DESC, "returnDate"));
            retVal = mongoTemplate.find(q, Lending.class);
        }

        return retVal;
    }

    public List<Object> getGroupByForLendingsBetweenDate(Date start, Date end, String location, String groupByField, String countFieldName, String sortByField){
        List<Object> results = null;
        Criteria lendDateCriteria = Criteria.where("lendDate").gt(DateUtils.getYesterday(DateUtils.getEndOfDay(start))).lte(DateUtils.getEndOfDay(end));
        if (location != null && !location.equals(""))
            lendDateCriteria = new Criteria().andOperator(lendDateCriteria, Criteria.where("location").is(location));

        Aggregation agg = Aggregation.newAggregation(Aggregation.match(lendDateCriteria),
                Aggregation.group(groupByField).count().as(countFieldName),
                Aggregation.project(countFieldName).and(groupByField).previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, sortByField)
        );
        results = mongoTemplate.aggregate(agg, Lending.class, Object.class).getMappedResults();

        if (results != null && results.size() >= 20)
            return results.subList(0, 20);
        else
            return results;
    }


    @Override
    public List<String> getLendingsUserId(String ctlgNo, String librarianLend, String librarianReturn, String location,
                                  Date lendDateStart, Date lendDateEnd, Date returnDateStart, Date returnDateEnd,
                                  Date deadlineStart, Date deadlineEnd) {
        Criteria criteria = createCriteria(lendDateStart, lendDateEnd, returnDateStart, returnDateEnd, location);
        Criteria ctlgNoC, librarianLendC, librarianReturnC, deadlineC;
        Query q=new Query();
        if (ctlgNo != null) {

            ctlgNoC = Criteria.where("ctlgNo").regex(ctlgNo, "i");
            if (criteria!=null)
              criteria = new Criteria().andOperator(criteria, ctlgNoC);
            else{
                criteria = ctlgNoC;
            }
        }
        if (librarianLend != null) {
            librarianLendC = Criteria.where("librarianLend").regex(librarianLend,"i");
            if (criteria!=null)
                criteria = new Criteria().andOperator(criteria, librarianLendC);
            else{
                criteria = librarianLendC;
            }
        }
        if (librarianReturn != null) {
            librarianReturnC = Criteria.where("librarianReturn").regex(librarianReturn,"i");
            if (criteria!=null)
                criteria = new Criteria().andOperator(criteria, librarianReturnC);
            else{
                criteria = librarianReturnC;
            }
        }
        if (deadlineStart != null) {
            deadlineC = Criteria.where("deadline").gte(deadlineStart).lte(deadlineEnd);
            if (criteria!=null)
                criteria = new Criteria().andOperator(criteria, deadlineC);
            else{
                criteria = deadlineC;
            }
        }
        if (criteria!=null){
            q.addCriteria(criteria);
            List<Lending> l = mongoTemplate.find(q, Lending.class);
            return l.stream().map(i -> i.getUserId()).collect(Collectors.toList());
        }else{
            return null;
        }

    }
}
