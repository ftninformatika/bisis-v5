package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.search.SearchModelMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

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

        List signDates = (List)searchModel.getValueForPrefix("signings.signDate");
        List untilDates = (List)searchModel.getValueForPrefix("signings.untilDate");
        if (signDates !=null){
            if(currentCriteria!=null){
                currentCriteria = currentCriteria.andOperator(Criteria.where("signings.signDate").gte(signDates.get(0)).lte(signDates.get(1)));
            }else{
                currentCriteria = Criteria.where("signings.signDate").gte(signDates.get(0)).lte(signDates.get(1));
            }

        }
        if (untilDates !=null){
            if(currentCriteria!=null) {
                currentCriteria.andOperator(Criteria.where("signings.untilDate").gte(untilDates.get(0)).lte(untilDates.get(1)));
            }else{
                currentCriteria = Criteria.where("signings.untilDate").gte(untilDates.get(0)).lte(untilDates.get(1));
            }
        }
        if (userIds !=null) {
            if (currentCriteria != null) {
                currentCriteria = currentCriteria.andOperator(Criteria.where("userId").in(userIds));
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


//TODO ne obradjujem slučaj kada unese zvedzdicu u smislu wildcard-a
    private Criteria createCriteria(String prefix,String text, String op, Criteria currentCriteria){
        if(!text.equals("")&& !fromLendings.contains(prefix)){
            Criteria c=Criteria.where(prefix).is(text);
            if(currentCriteria==null){
                currentCriteria=c;
            }else {
                if (currentOperator.equalsIgnoreCase("and")) {
                    currentCriteria = currentCriteria.andOperator(c);
                } else if (currentOperator.equalsIgnoreCase("or")){
                    currentCriteria = currentCriteria.orOperator(c);
                }else{
                    currentCriteria=currentCriteria.andOperator(c.not());
                }
            }
            currentOperator=op;
        }
        return currentCriteria;
    }
}
