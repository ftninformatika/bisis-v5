package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.search.SearchModelMember;
import com.ftninformatika.utils.RegexUtils;
import com.ftninformatika.utils.date.DateUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by dboberic on 22/11/2017.
 */

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    @Autowired MongoTemplate mongoTemplate;
    List<String> fromLendings = Arrays.asList("ctlgNo", "librarianLend", "librarianReturn", "lendDate", "returnDate", "deadline");
    String currentOperator = "and";


 /*   db.bgb_members.find( { signings: { $elemMatch: { untilDate:{$gt:ISODate("2022-05-24T00:00:00.110Z"),$lte: ISODate("2022-05-27T00:00:00.110Z")}}},
        "signings.untilDate":{$not:{$gt: ISODate("2022-05-27T00:00:00.110Z")}}
    })*/
    public List getExpiredMemebershipForOpacUsers(LocalDateTime start, LocalDateTime end, String library){
        List<AggregationOperation> pipeline = new ArrayList<AggregationOperation>();
        Criteria arrayCriteria = Criteria.where("untilDate").gte(start).lte(end);
        Criteria criteria = Criteria.where("signings").elemMatch(arrayCriteria).and("activatedWebProfile").is(true).and("signings.untilDate").not().gt(end);
        MatchOperation matchOp = Aggregation.match(criteria);
        ProjectionOperation projectOp = Aggregation.project("_id").and(ConvertOperators.ToString.toString("$_id")).as("id");
        ProjectionOperation projectOpId = Aggregation.project("id").andExclude("_id");
        pipeline.add(matchOp);
        pipeline.add(projectOp);
        pipeline.add(projectOpId);
        Aggregation aggregation = Aggregation.newAggregation(pipeline.toArray(new AggregationOperation[pipeline.size()]));
        AggregationOptions aggregationOptions = Aggregation.newAggregationOptions().cursorBatchSize(10000).allowDiskUse(true).build();
        AggregationResults<HashMap> output = mongoTemplate.aggregate(aggregation.withOptions(aggregationOptions), library + "_members", HashMap.class);
        List<HashMap> ids =output.getMappedResults();
        return ids.stream().map(id->id.get("id")).collect(Collectors.toList());
    }
    public Map<String, Integer> getLibrarianSignedCount(Date start, Date end, String location) {
        List<Member> members = getSignedMembers(start, end, location, "userId");
        Map<String, Integer> mp = new HashMap<>();
        for (Member m : members) {
            String librarian = m.getSignings().get(0).getLibrarian();
            if (mp.containsKey(librarian))
                mp.put(librarian, mp.get(librarian) + 1);
            else
                mp.put(librarian, 1);
        }

        return mp;
    }

    public Report getPictureBooksReport(Date start, Date end, String location) {
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

        if (m != null) {
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
        Query q = new Query();
        Criteria currentCriteria = null;
        if (searchModel.getText1() != null) {
            currentCriteria = createORCriteria(searchModel.getPref1(), searchModel.getText1());
            currentOperator = searchModel.getOper1();
        }
        if (searchModel.getText2() != null) {
            Criteria orCriteria = createORCriteria(searchModel.getPref2(), searchModel.getText2());
            if (currentCriteria != null){
                currentCriteria = createCriteria(currentCriteria,orCriteria);
            }else {
                currentCriteria = orCriteria;
            }
            currentOperator = searchModel.getOper2();
        }
        if (searchModel.getText3() != null) {
            Criteria orCriteria = createORCriteria(searchModel.getPref3(), searchModel.getText3());
            if (currentCriteria != null){
                currentCriteria = createCriteria(currentCriteria,orCriteria);
            }else {
                currentCriteria = orCriteria;
            }
            currentOperator = searchModel.getOper3();
        }
        if (searchModel.getText4() != null) {
            Criteria orCriteria = createORCriteria(searchModel.getPref4(), searchModel.getText4());
            if (currentCriteria != null){
                currentCriteria = createCriteria(currentCriteria,orCriteria);
            }else {
                currentCriteria = orCriteria;
            }
            currentOperator = searchModel.getOper4();
        }

        if (searchModel.getText5() != null) {
            Criteria orCriteria = createORCriteria(searchModel.getPref5(), searchModel.getText5());
            if (currentCriteria != null){
                currentCriteria = createCriteria(currentCriteria,orCriteria);
            }else {
                currentCriteria = orCriteria;
            }
        }
        Object[] signDates = (Object[]) searchModel.getValueForPrefix("signings.signDate");
        Object[] untilDates = (Object[]) searchModel.getValueForPrefix("signings.untilDate");
        if (signDates != null) {
           Criteria signDate = Criteria.where("signDate").gte(signDates[0]).lte(signDates[1]);
            Criteria signingCriteria = Criteria.where("signings").elemMatch(signDate);
            if (signDates[2] != null && !signDates[2].equals("")) {
                signingCriteria = Criteria.where("signings").elemMatch(new Criteria().andOperator(signDate,Criteria.where("location").is(signDates[2])));
            }
            if (currentCriteria != null) {
                currentCriteria = new Criteria().andOperator(currentCriteria, signingCriteria);
            } else {
                currentCriteria = signingCriteria;
            }
        }

        if (untilDates != null) {
            Criteria untilDate = Criteria.where("untilDate").gte(untilDates[0]).lte(untilDates[1]);
            Criteria signingCriteria = Criteria.where("signings").elemMatch(untilDate);
            if (untilDates[2] != null && !untilDates[2].equals("")) {
                signingCriteria = Criteria.where("signings").elemMatch(new Criteria().andOperator(untilDate,Criteria.where("location").is(untilDates[2])));
            }
            if (currentCriteria != null) {
                currentCriteria = new Criteria().andOperator(currentCriteria, signingCriteria);
            } else {
                currentCriteria = signingCriteria;
            }
        }
        if (userIds != null) {
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
        } else
        {
            return null;
        }
    }


    public List<Member> getBlockedMembers(String location) {
        List<Member> retVal = new ArrayList<>();

        Criteria cr = new Criteria();
        cr = cr.andOperator(Criteria.where("blockReason").ne(null), Criteria.where("blockReason").ne(""));
        if (location != null && !location.equals(""))
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));

        if (cr != null) {
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
        Criteria cr = null;
        if (location != null && !location.equals("")) {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        } else {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        if (cr != null) {
            Query q = new Query();
            q.addCriteria(cr);
            Criteria elemMatchInsideCriteria = new Criteria();
            elemMatchInsideCriteria = elemMatchInsideCriteria.andOperator(Criteria.where("signDate").gte(startDate).lte(endDate), Criteria.where("location").is(location));
            q.fields().elemMatch("signings", elemMatchInsideCriteria);
            q.fields().include("userId").include("firstName").include("lastName").include("address").include("zip").
                    include("city").include("docNo").include("docCity").include("jmbg").include("gender").
                    include("userCategory.description").include("membershipType.description");
            q.with(new Sort(new Sort.Order(Sort.Direction.ASC, sortBy)));
            List<Member> m = mongoTemplate.find(q, Member.class);
            return m;
        } else {
            return null;
        }
    }


    @Override
    public List<Member> getSignedCorporateMembers(Date startDate, Date endDate, String institution, String location) {
        Criteria cr = null;
        if (location != null && !location.equals("")) {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        } else {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        if (cr != null) {
            Query q = new Query();
            q.addCriteria(cr.and("corporateMember.instName").is(institution));
            List<Member> m = mongoTemplate.find(q, Member.class);
            return m;
        } else {
            return null;
        }
    }

    public Long getFreeSigningMembersCount(Date start, Date end, String location, boolean firstTimeSigned) {
        Criteria cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(DateUtils.getStartOfDay(start)).lte(DateUtils.getEndOfDay(end)).and("cost").is(0));
        if (location != null && !location.equals("")) {
            cr = new Criteria().andOperator(cr, Criteria.where("location").is(location));
        }
        if (firstTimeSigned)
            cr = new Criteria().andOperator(cr, Criteria.where("signings").size(1));
        Query query = new Query();
        query.addCriteria(cr);

        Long count = mongoTemplate.count(query, Member.class);
        return count;
    }

    public Integer getUserSignedCount(Date start, Date end, String location, boolean firstTimeSigned) {
        Criteria cr;
        start = DateUtils.getYesterday(DateUtils.getEndOfDay(start));
        end = DateUtils.getNextDay(DateUtils.getStartOfDay(end));
        if (location != null && !location.equals("")) {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gt(start).lt(end).and("location").is(location));

        } else {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gt(start).lt(end));
        }
        if (firstTimeSigned)
            cr = new Criteria().andOperator(cr, Criteria.where("signings").size(1));
        Query query = new Query();
        query.addCriteria(cr);

        int cnt = 0;
        List<Member> memberList = mongoTemplate.find(query, Member.class);
        //ako je nekako isti clan upisan vise puta u zadatom periodu!
        for (Member m : memberList)
            for (Signing s : m.getSignings())
                if (s.getSignDate().after(start) && s.getSignDate().before(end))
                    cnt++;
        return cnt;
    }

    public List<Report> groupMemberByField(Date startDate, Date endDate, String location, String groupByField) {
        return groupMemberByField(startDate, endDate, location, groupByField, false);
    }

    public List<Report> groupMemberByField(Date startDate, Date endDate, String location, String groupByField, boolean firstTimeSigned) {
        Criteria cr = null;
        if (location != null && !location.equals("")) {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate).and("location").is(location));

        } else {
            cr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(startDate).lte(endDate));
        }
        if (firstTimeSigned) {
            cr = new Criteria().andOperator(cr, Criteria.where("signings").size(1));
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

    public List<String> getVisitorsUserIds(Date start, Date end, String location, String library) {
        List<String> retVal = new ArrayList<>();
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        //uclanjeni
        Criteria signedCr = Criteria.where("signings").elemMatch(Criteria.where("signDate").gte(start).lte(end));
        //zaduzili, produzili, razduzili
        Criteria lendingActionCr = new Criteria().orOperator(
                Criteria.where("lendDate").gte(start).lte(end),
                Criteria.where("resumeDate").gte(start).lte(end),
                Criteria.where("returnDate").gte(start).lte(end)
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

        Set<String> signedMembersDistinct =  new HashSet<Member>(mongoTemplate
                .find(qSigned, Member.class)).stream().map(m -> m.getUserId()).collect(Collectors.toSet());

        Set<String> lendingMembersDistinct = mongoTemplate
                .find(qLending, Lending.class).stream().map(l -> l.getUserId()).collect(Collectors.toSet());

        retVal.addAll(signedMembersDistinct);
        retVal.addAll(lendingMembersDistinct);
        retVal = retVal.stream().distinct().collect(Collectors.toList());
        retVal = retVal.stream().sorted().collect(Collectors.toList());

        return retVal;
    }
    private Criteria createORCriteria(String prefix, String text) {
        if (text != null && !text.equals("") && !fromLendings.contains(prefix)) {

            if (SearchModelMember.PREDEFINED_VALUE_PREFIXES.contains(prefix)) {
                text = RegexUtils.escapeSpecialRegexChars(text);
            }
            if (!text.startsWith("*")) {
                text = "^" + text;
            }
            if (!text.endsWith("*")) {
                text = text + "$";
            }
            text = text.replace("*", "");

            Criteria c1 = Criteria.where(prefix).regex(LatCyrUtils.toLatin(text), "i");
            Criteria c2 = Criteria.where(prefix).regex(LatCyrUtils.toCyrillic(text), "i");
            Criteria newCriteria = new Criteria();
            if (currentOperator.equalsIgnoreCase("not")){
                newCriteria.norOperator(c1,c2);
            }else {
                newCriteria.orOperator(c1,c2);
            }
        return newCriteria;
        }
        return null;
    }
    private Criteria createCriteria(Criteria currentCriteria, Criteria rightCriteria) {
        if (currentCriteria == null) {
            currentCriteria = rightCriteria;
        } else {
            Criteria newCriteria = new Criteria();
            if (currentOperator.equalsIgnoreCase("or")) {
                currentCriteria = newCriteria.orOperator(currentCriteria, rightCriteria);
            } else {
                currentCriteria = newCriteria.andOperator(currentCriteria, rightCriteria);
            }
        }
        return currentCriteria;
    }
//ovo se vise ne koristi, ali nek ostane za svaki slucaj
        private Criteria createCriteria(String prefix, String text, String op, Criteria currentCriteria) {

        if (text != null && !text.equals("") && !fromLendings.contains(prefix)) {

            if (SearchModelMember.PREDEFINED_VALUE_PREFIXES.contains(prefix)) {
                text = RegexUtils.escapeSpecialRegexChars(text);
            }
            if (!text.startsWith("*")) {
                text = "^" + text;
            }
            if (!text.endsWith("*")) {
                text = text + "$";
            }
            text = text.replace("*", "");

            Criteria c = Criteria.where(prefix).regex(text, "i");
            if (currentCriteria == null) {
                currentCriteria = c;
            } else {
                Criteria newCriteria = new Criteria();
                if (currentOperator.equalsIgnoreCase("and")) {
                    currentCriteria = newCriteria.andOperator(currentCriteria, c);
                } else if (currentOperator.equalsIgnoreCase("or")) {
                    currentCriteria = newCriteria.orOperator(currentCriteria, c);
                } else {
                    c = Criteria.where(prefix).ne(text);
                    currentCriteria = newCriteria.andOperator(currentCriteria, c);
                }
            }
            currentOperator = op;
        }
        return currentCriteria;
    }
}
