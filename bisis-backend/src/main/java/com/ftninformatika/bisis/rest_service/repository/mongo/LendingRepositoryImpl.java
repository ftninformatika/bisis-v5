package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.utils.date.DateUtils;
import freemarker.template.utility.DateUtil;
import org.elasticsearch.index.query.QueryBuilder;
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
            if (location!=null && !location.equals("") ) {
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

    public Long getPassiveVisitorsCount(Date start, Date end, String location){
        List<Object> retVal = null;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);

        Criteria lendDateCr = //Criteria.where("returnDate").gte(start).lte(end);
                                Criteria.where(" ( (new Date(this.lendDate) > new Date(ISODate(\"2013-11-19T14:00:00Z\"))) && (new Date(this.lendDate) < new Date(ISODate(\"2014-12-19T14:00:00Z\"))))");
        if(location != null && !location.equals(""))
            lendDateCr = new Criteria().andOperator(lendDateCr, Criteria.where("location").is(location));

        //lendDateCr = new Criteria().andOperator(lendDateCr, Criteria.where("returnDate").lte("new Timestamp(this.lendDate, 1)"));
        Criteria retDateCr = Criteria.where("returnDate").exists(true);
        retDateCr = new Criteria().andOperator(retDateCr,Criteria.where("new Date(this.lendDate) >=  new Date(this.returnDate"), Criteria.where("new Date(this.lendDate) <=  new Date(this.returnDate.getTime() + 86400000"));
        //lendDateCr = new Criteria().andOperator(lendDateCr, retDateCr);
        Query q = new Query();
        q.addCriteria(lendDateCr);
        List<Lending> lendings = mongoTemplate.find(q, Lending.class);
//        Aggregation agg = Aggregation.newAggregation(Aggregation.match(lendDateCr),
//                Aggregation.project("lendDate").and("userId").previousOperation(),
//                Aggregation.sort(Sort.Direction.DESC, "count")
//        );
//        List<Object> results = mongoTemplate.aggregate(agg, Lending.class, Object.class).getMappedResults();

        return null;
    }

    public Long getLendCount(Date start, Date end, String location){
        Long retVal = null;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr = new Criteria().orOperator(Criteria.where("lendDate").gte(start).lte(end),
                Criteria.where("resumeDate").gte(start).lte(end));
        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        Query q = new Query();
        q.addCriteria(cr);

        retVal = mongoTemplate.count(q, Lending.class);

        return retVal;
    }

    public Long getReturnCount(Date start, Date end, String location){
        Long retVal = null;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr =Criteria.where("returnDate").gte(start).lte(end);

        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        Query q = new Query();
        q.addCriteria(cr);

        retVal = mongoTemplate.count(q, Lending.class);

        return retVal;
    }

//    private getPassiveCriteria(Date start, Date end, String location){
//        start = DateUtils.getStartOfDay(start);
//        end = DateUtils.getEndOfDay(end);
//        Criteria c = Criteria.where("returnDate").gte(start).lte(end);
//        Criteria c2 = Criteria.where("lendDate").
//
//    }

    public Long getActiveVisitorsCount(Date start, Date end, String location){
        Long retVal = null;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria and1 = new Criteria().andOperator(Criteria.where("lendDate").gt(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)),
                Criteria.where("returnDate").is(null));
        Criteria or1 = new Criteria().orOperator(and1, Criteria.where("returnDate").gt(DateUtils.getEndOfDay(end)));

        Criteria and2 = new Criteria().andOperator(Criteria.where("resumeDate").gt(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)),
                Criteria.where("returnDate").is(null));
        Criteria or2 = new Criteria().orOperator(and2, Criteria.where("resumeDate").gt(DateUtils.getEndOfDay(end)));
        Criteria finalCriteria = new Criteria().orOperator(or1, or2);

        if (location != null && !location.equals(""))
            finalCriteria = new Criteria().andOperator(finalCriteria, Criteria.where("location").is(location));

        Query q = new Query();
        q.addCriteria(finalCriteria);
        retVal = mongoTemplate.count(q, Lending.class);
        return retVal;
    }

    public List<Object> getActiveVisitors(Date start, Date end, String location){
        List<Lending> retVal = null;
        Criteria and1 = new Criteria().andOperator(Criteria.where("lendDate").gt(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)),
                        Criteria.where("returnDate").is(null));
        Criteria or1 = new Criteria().orOperator(and1, Criteria.where("returnDate").gt(DateUtils.getEndOfDay(end)));

        Criteria and2 = new Criteria().andOperator(Criteria.where("resumeDate").gt(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)),
                                                    Criteria.where("returnDate").is(null));
        Criteria or2 = new Criteria().orOperator(and2, Criteria.where("resumeDate").gt(DateUtils.getEndOfDay(end)));
        Criteria finalCriteria = new Criteria().orOperator(or1, or2);

        if (location != null && !location.equals(""))
            finalCriteria = new Criteria().andOperator(finalCriteria, Criteria.where("location").is(location));


        Aggregation agg = Aggregation.newAggregation(Aggregation.match(finalCriteria),
                Aggregation.group("userId").count().as("count"),
                Aggregation.project("count").and("userId").previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, "count")
        );
        List<Object> results = mongoTemplate.aggregate(agg, Lending.class, Object.class).getMappedResults();

        return results;
    }

//    public List<Object> getPassiveVisitors(Date start, Date end, String location){
//        List<Lending> retVal = null;
//        Criteria and1 = new Criteria().andOperator(Criteria.where("lendDate").gt(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)),
//                Criteria.where("returnDate").is(null));
//
//
//        if (location != null && !location.equals(""))
//            finalCriteria = new Criteria().andOperator(finalCriteria, Criteria.where("location").is(location));
//
//
//        Aggregation agg = Aggregation.newAggregation(Aggregation.match(finalCriteria),
//                Aggregation.group("userId").count().as("count"),
//                Aggregation.project("count").and("userId").previousOperation(),
//                Aggregation.sort(Sort.Direction.DESC, "count")
//        );
//        List<Object> results = mongoTemplate.aggregate(agg, Lending.class, Object.class).getMappedResults();
//
//        return results;
//    }

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
