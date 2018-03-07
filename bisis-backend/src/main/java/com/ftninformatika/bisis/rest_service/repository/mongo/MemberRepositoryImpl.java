package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.PictureBook;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.search.SearchModelMember;
import com.ftninformatika.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by dboberic on 22/11/2017.
 */

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;
    List<String> fromLendings = Arrays.asList("ctlgNo","librarianLend","librarianReturn","lendDate","returnDate","deadline");
    String currentOperator=null;



    public Map<String, Integer> getLibrarianSignedCount(Date start, Date end, String location){
        List<Member> members = getSignedMembers(start, end, location, "userId");
        Map<String, Integer> mp = new HashMap<>();
        for(Member m: members){
            String librarian = m.getSignings().get(0).getLibrarian();
            if (mp.containsKey(librarian))
                mp.put(librarian, mp.get(librarian) + 1);
            else
                mp.put(librarian, 1);
        }

        return mp;
    }

    public Report getPictureBooksReport(Date start, Date end, String location){
        Report retVal = new Report();
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);

        Criteria cr = Criteria.where("picturebooks").elemMatch(Criteria.where("lendDate").gte(start).lte(end));
        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        Query q = new Query();
        q.addCriteria(cr);
        q.fields().include("picturebooks");
        List<Member> m = mongoTemplate.find(q, Member.class);

        int usrCount = 0;
        final int[] sumLend = {0};
        final int[] returnNo = {0};

        if (m != null){
            usrCount = m.size();
            m.forEach(
                    mem -> {
                        mem.getPicturebooks().forEach(
                                pb -> {
                                    sumLend[0] += pb.getLendNo();
                                    returnNo[0] += pb.getReturnNo();
                                }
                        );
                    }
            );
        }

        retVal.setProperty1(String.valueOf(usrCount));
        retVal.setProperty2(String.valueOf(sumLend[0]));
        retVal.setProperty3(String.valueOf(returnNo[0]));

        return retVal;
    }

    @Override
    public List<Member> getMembersFilteredByLending(SearchModelMember searchModel, List userIds) {
        Query q=new Query();
        Criteria currentCriteria=null;

        currentCriteria = createCriteria(searchModel.getPref1(),searchModel.getText1(),searchModel.getOper1(),currentCriteria);
        currentCriteria = createCriteria(searchModel.getPref2(),searchModel.getText2(),searchModel.getOper2(),currentCriteria);
        currentCriteria = createCriteria(searchModel.getPref3(),searchModel.getText3(),searchModel.getOper3(),currentCriteria);
        currentCriteria = createCriteria(searchModel.getPref4(),searchModel.getText4(),searchModel.getOper4(),currentCriteria);
        currentCriteria = createCriteria(searchModel.getPref5(),searchModel.getText4(),"and",currentCriteria);

        Object[] signDates = (Object[])searchModel.getValueForPrefix("signings.signDate");
        Object[] untilDates = (Object[])searchModel.getValueForPrefix("signings.untilDate");
        if (signDates !=null){
            Criteria signDate=Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(signDates[0]).lte(signDates[1]));
            if (signDates[2]!=null  && signDates[2].equals("")) {
                signDate=signDate.and("location").is("location");
            }
            if(currentCriteria!=null){
                currentCriteria = new Criteria().andOperator(currentCriteria,signDate);
            }else{
                currentCriteria = signDate;
            }

        }

        if (untilDates !=null){
            Criteria untilDate=Criteria.where("signings").elemMatch(Criteria.where("untilDate").gte(untilDates[0]).lte(untilDates[1]));
            if (untilDates[2]!=null && untilDates[2].equals("") ) {
                untilDate = untilDate.and("location").is(untilDates[2]);
            }
            if(currentCriteria!=null) {
                currentCriteria = new Criteria().andOperator(currentCriteria,untilDate );
            }else{
                currentCriteria = untilDate;
            }


        }
        if (userIds !=null) {
            if (currentCriteria != null) {
                currentCriteria = new Criteria().andOperator(currentCriteria, Criteria.where("userId").in(userIds));
            } else {
                currentCriteria = Criteria.where("userId").in(userIds);
            }
        }
        if (currentCriteria != null) {
            q.addCriteria(currentCriteria);
            List<Member> m = mongoTemplate.find(q, Member.class);
            return m;
        }else{
            return null;
        }
    }

    public List<Member> getBlockedMembers(String location){
        List<Member> retVal = new ArrayList<>();

        Criteria cr = new Criteria();
        cr = cr.andOperator(Criteria.where("blockReason").ne(null), Criteria.where("blockReason").ne(""));
        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        if (cr != null){
            Query q = new Query();
            q.addCriteria(cr);
            q.with(new Sort(new Sort.Order(Sort.Direction.DESC, "blockReason")));
            retVal = mongoTemplate.find(q, Member.class);
        }
        return retVal;
    }

    @Override
    public List<Member> getSignedMembers(Date startDate, Date endDate, String location, String sortBy) {
        startDate = DateUtils.getStartOfDay(startDate);
        endDate = DateUtils.getEndOfDay(endDate);
         Criteria cr=null;
        if (location!=null&& !location.equals("") ) {
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        }else{
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        if (cr != null) {
            Query q = new Query();
            q.addCriteria(cr);
            q.fields().elemMatch("signings",Criteria.where("signDate").gte(startDate).lte(endDate));
            q.fields().include("userId").include("firstName").include("lastName").include("address").include("zip").
                    include("city").include("docNo").include("docCity").include("jmbg").include("gender").
                    include("userCategory.description").include("membershipType.description");
            q.with(new Sort(new Sort.Order(Sort.Direction.ASC, sortBy)));
            List<Member> m = mongoTemplate.find(q, Member.class);
            return m;
        }else{
            return null;
        }
    }


    @Override
    public List<Member> getSignedCorporateMembers(Date startDate, Date endDate, String institution, String location) {
        Criteria cr=null;
        if (location!=null&& !location.equals("") ) {
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        }else{
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        if (cr != null) {
            Query q = new Query();
            q.addCriteria(cr.and("corporateMember.instName").is(institution));
            List<Member> m = mongoTemplate.find(q, Member.class);
            return m;
        }else{
            return null;
        }
    }

    public Long getFreeSigningMembersCount(Date start, Date end, String location){
        Criteria cr, cr1,cr2;
        if (location!=null && !location.equals("") ) {
            cr1 =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(DateUtils.getStartOfDay(start))).lte(DateUtils.getEndOfDay(end)).and("location").is(location);

        }else{
            cr1 =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)));
        }
        cr2 = Criteria.where("signings.cost").is(0);
        cr = new Criteria().andOperator(cr1, cr2);

        Query query = new Query();
        query.addCriteria(cr);

        Long count = mongoTemplate.count(query, Member.class);
        return count;
    }

    public Integer getUserSignedCount(Date start, Date end, String location){
        Criteria cr1;
        start = DateUtils.getYesterday(DateUtils.getEndOfDay(start));
        end = DateUtils.getNextDay(DateUtils.getStartOfDay(end));
        if (location!=null && !location.equals("") ) {
            cr1 =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gt(start).lt(end).and("location").is(location));

        }else{
            cr1 =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gt(start).lt(end));
        }
        Query query = new Query();
        query.addCriteria(cr1);

        int cnt = 0;
        List<Member> memberList = mongoTemplate.find(query, Member.class);
        //ako je nekako isti clan upisan vise puta u zadatom periodu!
        for(Member m: memberList)
            for(Signing s: m.getSignings())
                if(s.getSignDate().after(start) && s.getSignDate().before(end))
                    cnt++;
        return cnt;
    }

    public List<Report> groupMemberByField(Date startDate, Date endDate, String location, String groupByField){
        Criteria cr=null;
        if (location!=null && !location.equals("") ) {
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        }else{
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        Aggregation agg = newAggregation(
                match(cr),
                group(groupByField).count().as("property21"),
                project("property21").and("_id").as("property1")
                );

        AggregationResults<Report> groupResults = mongoTemplate.aggregate(agg, Member.class, Report.class);
        List<Report> result = groupResults.getMappedResults();

        return result;
    }

    public List<String> getVisitorsUserIds(Date start, Date end, String location, String library){
        List<String> retVal = new ArrayList<>();
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        //uclanjeni
        Criteria signedCr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(start).lte(end));
        //zaduzili, produzili, razduzili
        Criteria lendingActionCr = new Criteria().orOperator(
            Criteria.where("lendDate").gte(start).lte(end),
            Criteria.where("resumeDate").gte(start).lte(end),
            Criteria.where("resumeDate").gte(start).lte(end)
        );
        if (location != null && !location.equals("")) {
            signedCr = new Criteria().andOperator(signedCr, Criteria.where("location").is(location));
            lendingActionCr = new Criteria().andOperator(lendingActionCr, Criteria.where("location").is(location));
        }
        Query qSigned = new Query();
        qSigned.addCriteria(signedCr);
        qSigned.fields().include("userId");

        Query qLending = new Query();
        qLending.addCriteria(lendingActionCr);
        qLending.fields().include("userId");

        String[] signedMembersDistinct = (String[]) mongoTemplate.getCollection(library.toLowerCase() + "_members")
                                                      .distinct("userId", qSigned.getQueryObject()).toArray(new String[] {});
        String[] lendingMembersDistinct = (String[]) mongoTemplate.getCollection(library + "_lendings")
                                                       .distinct("userId", qLending.getQueryObject()).toArray(new String[] {});

        retVal.addAll(Arrays.asList(signedMembersDistinct));
        retVal.addAll(Arrays.asList(lendingMembersDistinct));
        retVal = retVal.stream().distinct().collect(Collectors.toList());
        retVal = retVal.stream().sorted().collect(Collectors.toList());

        return retVal;
    }

    private Criteria createCriteria(String prefix,String text, String op, Criteria currentCriteria){

        if(text != null && !text.equals("") && !fromLendings.contains(prefix)){
            if(!text.startsWith("*")){
                text="^"+text;
            }
            if (!text.endsWith("*")){
                text=text+"$";
            }
            text =text.replace("*","");

            Criteria c=Criteria.where(prefix).regex(text, "i");
            if(currentCriteria==null){
                currentCriteria=c;
            }else {
                Criteria newCriteria=new Criteria();
                if (currentOperator.equalsIgnoreCase("and")) {
                    currentCriteria =newCriteria.andOperator(currentCriteria,c);
                } else if (currentOperator.equalsIgnoreCase("or")){
                    currentCriteria =newCriteria.orOperator(currentCriteria,c);
                }else{
                    c=Criteria.where(prefix).ne(text);
                    currentCriteria =newCriteria.andOperator(currentCriteria,c);
                }
            }
            currentOperator=op;
        }
        return currentCriteria;
    }
}
