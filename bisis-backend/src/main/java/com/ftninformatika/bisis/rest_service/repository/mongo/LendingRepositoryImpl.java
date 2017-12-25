package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.library_configuration.Report;
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

    public List<Lending> getLenignsWithAnyActivityOnDate(Date start,Date end, String location){
        List<Lending> retVal = null;
        Criteria lendDateCriteria, returnDateCriteria, resumeDateCriteria, or, desiredCriteria;
        lendDateCriteria = Criteria.where("lendDate").gte(DateUtils.getYesterday(DateUtils.getEndOfDay(start))).lte(DateUtils.getEndOfDay(end));
        returnDateCriteria = Criteria.where("returnDate").gte(DateUtils.getYesterday(DateUtils.getEndOfDay(start))).lte(DateUtils.getEndOfDay(end));
        resumeDateCriteria = Criteria.where("resumeDate").gte(DateUtils.getYesterday(DateUtils.getEndOfDay(start))).lte(DateUtils.getEndOfDay(end));
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

    public Map<String, com.ftninformatika.bisis.circ.pojo.Report> getLibrarianStatistic(Date start, Date end, String location){
        //List<com.ftninformatika.bisis.circ.pojo.Report> retVal = null;
        Map<String, com.ftninformatika.bisis.circ.pojo.Report> reportMap = new HashMap<>();

        List<Object> lLend = getGroupByForLendingsBetweenDate(start, end, location,
                "librarianLend", "lendedCount", "librarianLend", "lendDate", null);

        List<Object> lReturn = getGroupByForLendingsBetweenDate(start, end, location,
                "librarianReturn", "returnedCount", "librarianReturn", "returnDate", null);

        List<Object> lResume = getGroupByForLendingsBetweenDate(start, end, location,
                "librarianResume", "resumedCount", "librarianResume", "resumeDate", null);

        lLend.forEach(
                l -> {
                    if (l!= null && l instanceof LinkedHashMap){
                        String libraran = ((LinkedHashMap)l).get("librarianLend").toString();
                        Integer count = (Integer) ((LinkedHashMap)l).get("lendedCount");

                        if (!reportMap.containsKey(libraran)){
                            com.ftninformatika.bisis.circ.pojo.Report r = new com.ftninformatika.bisis.circ.pojo.Report();
                            r.setProperty1(libraran);
                            r.setProperty2(String.valueOf(count));
                            reportMap.put(libraran, r);
                        }
                        else {
                            reportMap.get(libraran).setProperty2(String.valueOf(count));
                        }

                    }
                }
        );

        lReturn.forEach(
                l -> {
                    if (l!= null && l instanceof LinkedHashMap){
                        String libraran = ((LinkedHashMap)l).get("librarianReturn").toString();
                        Integer count = (Integer) ((LinkedHashMap)l).get("returnedCount");

                        if (!reportMap.containsKey(libraran)){
                            com.ftninformatika.bisis.circ.pojo.Report r = new com.ftninformatika.bisis.circ.pojo.Report();
                            r.setProperty1(libraran);
                            r.setProperty3(String.valueOf(count));
                            reportMap.put(libraran, r);
                        }
                        else {
                            reportMap.get(libraran).setProperty3(String.valueOf(count));
                        }

                    }
                }
        );

        lResume.forEach(
                l -> {
                    if (l!= null && l instanceof LinkedHashMap){
                        String libraran = ((LinkedHashMap)l).get("librarianResume").toString();
                        Integer count = (Integer) ((LinkedHashMap)l).get("resumedCount");

                        if (!reportMap.containsKey(libraran)){
                            com.ftninformatika.bisis.circ.pojo.Report r = new com.ftninformatika.bisis.circ.pojo.Report();
                            r.setProperty1(libraran);
                            r.setProperty4(String.valueOf(count));
                            reportMap.put(libraran, r);
                        }
                        else {
                            reportMap.get(libraran).setProperty4(String.valueOf(count));
                        }

                    }
                }
        );

        return reportMap;
    }

    public List<Object> getGroupByForLendingsBetweenDate(Date start, Date end, String location, String groupByField, String countFieldName, String sortByField, String byLendReturnResume, Integer listSize){
        List<Object> results = null;
        Criteria lendDateCriteria = Criteria.where(byLendReturnResume).gt(DateUtils.getYesterday(DateUtils.getEndOfDay(start))).lte(DateUtils.getEndOfDay(end));
        if (location != null && !location.equals(""))
            lendDateCriteria = new Criteria().andOperator(lendDateCriteria, Criteria.where("location").is(location));

        Aggregation agg = Aggregation.newAggregation(Aggregation.match(lendDateCriteria),
                Aggregation.group(groupByField).count().as(countFieldName),
                Aggregation.project(countFieldName).and(groupByField).previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, sortByField)
        );
        results = mongoTemplate.aggregate(agg, Lending.class, Object.class).getMappedResults();

        if (results != null && results.size() >= 20 && listSize != null)
            return results.subList(0, listSize);
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
