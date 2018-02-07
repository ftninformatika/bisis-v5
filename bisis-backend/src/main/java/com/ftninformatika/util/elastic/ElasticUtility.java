package com.ftninformatika.util.elastic;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.UniversalSearchModel;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.elasticsearch.index.query.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Petar on 7/17/2017.
 */
public class ElasticUtility {

    public static List<String> getIdsFromElasticIterable(Iterable<ElasticPrefixEntity> elasticResponse){
        return StreamSupport.stream(elasticResponse.spliterator(), false)
                .map(ElasticPrefixEntity::getId)
                .collect(Collectors.toList());
    }


    public static ElasticPrefixEntity getEPEFromCtlgno(String ctlgNo, Iterable<ElasticPrefixEntity> elasticPrefixEntities){
        final ElasticPrefixEntity[] retVal = {null};

        elasticPrefixEntities.forEach(
                ep -> {
                    if (ep.getPrefixes().get("IN") != null && ep.getPrefixes().size() > 0){
                        for (String ct: ep.getPrefixes().get("IN")) {
                            if (ct.equals(ctlgNo)){
                                retVal[0] = ep;
                                break;
                            }
                        }
                    }
                }
        );

        return retVal[0];
    }


    public static BoolQueryBuilder searchUniversalQuery(UniversalSearchModel universalSearchModel) {
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();

        if (universalSearchModel.getSearchText() != null && ! "".equals(universalSearchModel.getSearchText())){
            //retVal.must(QueryBuilders.simpleQueryStringQuery(universalSearchModel.getSearchText()));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.AU", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.TI", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.BN", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText()))); //isbn??
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.SP", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText()))); //issn
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.SB", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText()))); //predmetne odrednice
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.DC", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText()))); //udk??
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.KW", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.PP", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.PU", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText())));

            retVal.minimumNumberShouldMatch(universalSearchModel.getSearchText().split(" ").length);


        }

        if (universalSearchModel.getDepartments() != null && universalSearchModel.getDepartments().size() > 0){
            for (String dep :universalSearchModel.getDepartments()){
                retVal.must(QueryBuilders.matchQuery("prefixes.OD", dep));
            }
        }

        return retVal;
    }

    //formiranje Query-ja za glavnu pretragu zapisa
    public static BoolQueryBuilder makeQuery(SearchModel sm){

        //ovo je zbog vrste autorstva Pera Peric mentor, sve se trazi u jednom polju AU
        if (sm.getValueForPrefix("AU")!=null && !sm.getValueForPrefix("AU").equals("") && sm.getValueForPrefix("TA")!= null){
            sm.setValueForPrefix("AU",sm.getValueForPrefix("AU")+" "+sm.getValueForPrefix("TA"));
            sm.setValueForPrefix("TA","");
        }

        BoolQueryBuilder retVal = QueryBuilders.boolQuery();

        try {
            if (sm.getText1() != null && !"".equals(sm.getText1())) {
                QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccented(sm.getText1()));
                qb.defaultField("prefixes." + sm.getPref1());
                retVal.must(qb);
            }

            if (sm.getText2() != null && !"".equals(sm.getText2())) {
                QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccented(sm.getText2()));
                qb.defaultField("prefixes." + sm.getPref2());
                if ( "AND".equals(sm.getOper1()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper1())) {
                    retVal.should(QueryBuilders.matchPhraseQuery("prefixes." + sm.getPref2(), LatCyrUtils.toLatinUnaccented(sm.getText2())));
                }
                if ( "NOT".equals(sm.getOper1()))
                    retVal.mustNot(qb);
            }

            if (sm.getText3() != null && !"".equals(sm.getText3())) {
                QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccented(sm.getText3()));
                qb.defaultField("prefixes." + sm.getPref3());
                if ( "AND".equals(sm.getOper2()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper2()))
                    retVal.should(qb);
                if ( "NOT".equals(sm.getOper2()))
                    retVal.mustNot(qb);
            }

            if (sm.getText4() != null && !"".equals(sm.getText4())) {
                QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccented(sm.getText4()));
                qb.defaultField("prefixes." + sm.getPref4());
                if ( "AND".equals(sm.getOper3()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper3()))
                    retVal.should(qb);
                if ( "NOT".equals(sm.getOper3()))
                    retVal.mustNot(qb);
            }

            if (sm.getText5() != null && !"".equals(sm.getText5())) {
                QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccented(sm.getText5()));
                qb.defaultField("prefixes." + sm.getPref5());
                if ( "AND".equals(sm.getOper4()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper4()))
                    retVal.should(qb);
                if ( "NOT".equals(sm.getOper4()))
                    retVal.mustNot(qb);
            }

            if (sm.getDepartments() != null && sm.getDepartments().size() > 0){
                for (String dep :sm.getDepartments()){
                    retVal.must(QueryBuilders.matchQuery("prefixes.OD", dep));
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        return retVal;
    }


    //expand search query
    public static BoolQueryBuilder makeExpandQuery(String prefName, String prefValue){
        BoolQueryBuilder retVal = new BoolQueryBuilder();

        try{
            if ("".equals(prefName) || "".equals(prefValue))
                return null;

            retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes."+prefName, LatCyrUtils.toLatinUnaccented(prefValue) ));
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        return retVal;
    }


}
