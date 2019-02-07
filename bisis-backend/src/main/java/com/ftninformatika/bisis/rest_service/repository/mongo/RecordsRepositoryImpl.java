package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.RangeRegexGenerator;
import org.bson.BsonRegularExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecordsRepositoryImpl implements RecordsRepositoryCustom{

    @Autowired MongoTemplate mongoTemplate;

    private boolean matchesInvRange(String inv, List<String> regexStrings) {
        for (String reg: regexStrings) {
            if (Pattern.compile(reg).matcher(inv).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean validateInvNumHolesInput(String invFrom, String invTo) {
        try {
            Integer.parseInt(invFrom);
            Integer.parseInt(invTo);
            if (!invFrom.substring(0,4).equals(invTo.substring(0,4)))
                return false;
            int from = Integer.parseInt(invFrom.substring(4));
            int to = Integer.parseInt(invTo.substring(4));
            if (from < 1 || from > 9999999)
                return false;
            if (to < 2 || to > 9999999)
                return false;
            if (from >= to || (to - from) > 100000)
                return false;
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public List<Integer> findInvNumHoles(String invFrom, String invTo) {
        List<Integer> retVal = new ArrayList<>();
        if(!validateInvNumHolesInput(invFrom, invTo))
            return retVal;

        RangeRegexGenerator regexGenerator = new RangeRegexGenerator();
        List<String> regexes = regexGenerator.getRegex(invFrom, invTo);
        List<Criteria> regexCr = new ArrayList<>();
        for (String reg: regexes)
            regexCr.add(Criteria.where("primerci").elemMatch(Criteria.where("invBroj").regex(reg)));

        Criteria cr = new Criteria().orOperator(regexCr.toArray(new Criteria[regexCr.size()]));
        Query q = new Query();
        q.addCriteria(cr);
        q.fields().include("primerci.invBroj");
        Set<Integer> usedInvs = new HashSet<>();
        {
            List<Record> results = mongoTemplate.find(q, Record.class);
            for (Record r: results)
                for (Primerak p: r.getPrimerci())
                    if (matchesInvRange(p.getInvBroj(), regexes))
                        usedInvs.add(Integer.parseInt(p.getInvBroj().substring(4)));
        }
        retVal = IntStream.rangeClosed(Integer.parseInt(invFrom.substring(4)), Integer.parseInt(invTo.substring(4))).boxed().collect(Collectors.toList());
        retVal.removeAll(usedInvs);
        return retVal;
    }
}
