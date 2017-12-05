package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.search.SearchModelMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by dboberic on 22/11/2017.
 */

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;
    List<String> fromLendings = Arrays.asList("ctlgNo","librarianLend","librarianReturn","lendDate","returnDate","deadline");
    String currentOperator=null;

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

    @Override
    public List<Member> getSignedMembers(Date startDate, Date endDate, String location, String sortBy) {
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
                    include("city").include("docNo").include("docCity").include("jmbg").
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


    public List<Report> groupMemberByMembershipType(Date startDate, Date endDate, String location){
        Criteria cr=null;
        if (location!=null&& !location.equals("") ) {
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        }else{
            cr =  Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        Aggregation agg = newAggregation(
                match(cr),
                group("membershipType.description").count().as("property21"),
                project("property21").and("_id").as("property1")
                );

        AggregationResults<Report> groupResults = mongoTemplate.aggregate(agg, Member.class, Report.class);
        List<Report> result = groupResults.getMappedResults();

        return result;
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
