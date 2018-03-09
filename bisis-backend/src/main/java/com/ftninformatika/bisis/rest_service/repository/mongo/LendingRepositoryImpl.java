package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.utils.date.DateUtils;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.awt.image.CropImageFilter;
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
            if (location!=null && !location.equals("") ) {
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
            returnDate = Criteria.where("returnDate").ne(null);
            returnDate = new Criteria().andOperator(Criteria.where("returnDate").gte(startR).lte(endR), returnDate);
            if (location!=null && !location.equals("") ) {
                and = new Criteria();
                currentCriteria = and.andOperator(returnDate, Criteria.where("location").is(location));
            }else{
                currentCriteria = returnDate;
            }
        }
        return currentCriteria;
    }

    public List<Lending> getCtlgnoUsrId(Date start, Date end, String location){
        List<Lending> results = new ArrayList<>();
        Map<String, String> retVal = new HashMap<>();
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr = new Criteria().orOperator(Criteria.where("lendDate").gte(start).lte(end),
                Criteria.where("resumeDate").gte(start).lte(end));
        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        if (cr != null) {
            Query q = new Query();
            q.addCriteria(cr);
            q.fields().include("userId").include("ctlgNo");
            results = mongoTemplate.find(q, Lending.class);
        }

        return results;
    }

    public List<String> getLendActionsCtlgNos(Date start, Date end, String location, String library){
        List<String> retVal = new ArrayList<>();
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria actionCr = new Criteria().orOperator(
                Criteria.where("lendDate").gte(start).lte(end),
                Criteria.where("returnDate").gte(start).lte(end),
                Criteria.where("resumeDate").gte(start).lte(end)
        );
        if (location != null && !location.equals(""))
            actionCr = new Criteria().andOperator(actionCr, Criteria.where("location").is(location));

        Query query = new Query();
        query.addCriteria(actionCr);
        query.fields().include("ctlgNo");


        //ko munja naspram .find()
        DBCursor cursor =  mongoTemplate.getCollection(library.toLowerCase() + "_lendings")
                                         .find(query.getQueryObject());
        while(cursor.hasNext()){
            retVal.add(String.valueOf(cursor.next().get("ctlgNo")));
        }

        return retVal;
    }


    public List<Lending> getResumedLendings(Date start, Date end, String locaiton){
        List<Lending> retVal = null;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr = Criteria.where("resumeDate").gte(start).lte(end);
        if (locaiton != null && !locaiton.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(locaiton));
        Query q = new Query();
        q.addCriteria(cr);
        retVal = mongoTemplate.find(q, Lending.class);

        return retVal;
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

    /**
     * distinct broj korisnika se odnosi samo na dane
     */
    public Integer getLendMemberCountDistinctByDate(Date start, Date end, String location){
        Integer retVal = 0;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr = new Criteria().orOperator(Criteria.where("lendDate").gte(start).lte(end),
                Criteria.where("resumeDate").gte(start).lte(end));
        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        Query q = new Query();
        q.addCriteria(cr);
        q.fields().include("lendDate").include("resumeDate").include("userId");

        List<Lending> lendingList = mongoTemplate.find(q, Lending.class);
        retVal = distinctCountByDay(lendingList, 0);

        return retVal;
    }

    public Integer getReturnMemberCountDistinctByDate(Date start, Date end, String location){
        Integer retVal = 0;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr =Criteria.where("returnDate").gte(start).lte(end).ne(null);

        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        Query q = new Query();
        q.addCriteria(cr);
        q.fields().include("returnDate").include("userId");

        List<Lending> lendingList = mongoTemplate.find(q, Lending.class);
        retVal = distinctCountByDay(lendingList, 2);

        return retVal;
    }

    /**
     * TODO enumeracija
     *@param whichDate:
     *                0 - lendDate
     *                1 - resumeDate
     *                2 - returnDate
     */
    public static Integer distinctCountByDay(List<Lending> lendingList, Integer whichDate){
        Integer retVal = 0;
        Map<Date, List<String>> dateUserIdMap = new HashMap<>();
        for(Lending l: lendingList){
            Date d = null;
            if (whichDate == 0)
                d = DateUtils.getStartOfDay(l.getLendDate());
            else if (whichDate == 1)
                d = DateUtils.getStartOfDay(l.getResumeDate());
            else if (whichDate == 2)
                d = DateUtils.getStartOfDay(l.getReturnDate());


            if (dateUserIdMap.containsKey(d)){
                if(!dateUserIdMap.get(d).contains(l.getUserId()))
                    dateUserIdMap.get(d).add(l.getUserId());
            }
            else {
                dateUserIdMap.put(d, new ArrayList<>());
                dateUserIdMap.get(d).add(l.getUserId());
            }
        }

        for(Map.Entry<Date, List<String>> entry: dateUserIdMap.entrySet()){
            retVal += entry.getValue().size();
        }
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

    public Integer getLendingCountBy(String dateField, Date start, Date end, String location, String library, boolean distinct){
        Integer retVal = 0;
        if (dateField == null || dateField.equals(""))
            throw new RuntimeException("Proslediti validno datumsko polje!");

        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria cr = Criteria.where(dateField).gte(start).lte(end);
        if(location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));
        Query query = new Query();
        query.addCriteria(cr);
        query.fields().include("ctlgNo");
        List lendBooks = new ArrayList();
        if(!distinct)
            lendBooks = mongoTemplate.getCollection(library.toLowerCase() + "_lendings")
                                      .distinct("ctlgNo", query.getQueryObject());
        else
            lendBooks =mongoTemplate.getCollection(library.toLowerCase() + "_lendings")
                    .find(query.getQueryObject()).toArray();
        retVal = lendBooks.size();

        return retVal;
    }



    public List<Lending> getLenignsWithAnyActivityOnDate(Date start,Date end, String location){
        List<Lending> retVal = null;
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria lendDateCriteria, returnDateCriteria, resumeDateCriteria, or, desiredCriteria;
        lendDateCriteria = Criteria.where("lendDate").gte(start).lte(end);
        returnDateCriteria = Criteria.where("returnDate").gte(start).lte(end);
        resumeDateCriteria = Criteria.where("resumeDate").gte(start).lte(end);
        or = new Criteria();
        or.orOperator(lendDateCriteria, returnDateCriteria, resumeDateCriteria);

        if(location != null && !location.equals(""))
            desiredCriteria = or.andOperator(or, Criteria.where("location").is(location));
        else
            desiredCriteria = or;

        if (desiredCriteria != null){
            Query q = new Query();
            q.addCriteria(desiredCriteria);
            retVal = mongoTemplate.find(q, Lending.class);
        }

        return retVal;
    }

    public Map<String, com.ftninformatika.bisis.circ.pojo.Report> getLibrarianStatistic(Date start, Date end, String location){
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
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        Criteria lendDateCriteria = Criteria.where(byLendReturnResume).gte(start).lte(end);
        if (location != null && !location.equals(""))
            lendDateCriteria = new Criteria().andOperator(lendDateCriteria, Criteria.where("location").is(location));

        Aggregation agg = Aggregation.newAggregation(Aggregation.match(lendDateCriteria),
                Aggregation.group(groupByField).count().as(countFieldName),
                Aggregation.project(countFieldName).and(groupByField).previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, sortByField)
        );
        results = mongoTemplate.aggregate(agg, Lending.class, Object.class).getMappedResults();

        if (results != null && listSize != null && results.size() >= listSize )
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
