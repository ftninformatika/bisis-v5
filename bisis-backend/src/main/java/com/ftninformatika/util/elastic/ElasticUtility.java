package com.ftninformatika.util.elastic;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.UniversalSearchModel;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.queryparser.xml.FilterBuilderFactory;
import org.apache.lucene.search.Filter;
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

    private static QueryBuilder buildQbForField(String text, String prefix){
        QueryBuilder qb = null;
        if (text != null && !"".equals(text)) {

            //ako trazi pocetak teksta
            if(text.startsWith("~") && text.length() > 1)
                qb = QueryBuilders.matchPhrasePrefixQuery("prefixes." + prefix, text.substring(1)).analyzer("standard").maxExpansions(0);
            //ako trazi kraj teksta, obelezeno sa 0end0
            else if(text.endsWith("~") && text.length() > 1) {
//                qb = QueryBuilders.matchQuery("prefixes." + prefix, text.substring(0, text.length() - 1)
//                        + PrefixConverter.endPhraseFlag).analyzer("standard").maxExpansions(0);
                qb = QueryBuilders.queryStringQuery("*" + LatCyrUtils.toLatinUnaccented(text) + PrefixConverter.endPhraseFlag);
                ((QueryStringQueryBuilder)qb).defaultField("prefixes." + prefix);
                ((QueryStringQueryBuilder)qb).defaultOperator(QueryStringQueryBuilder.Operator.AND);
                ((QueryStringQueryBuilder)qb).autoGeneratePhraseQueries(true);
            }
            else {
                qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccented(text));
                ((QueryStringQueryBuilder)qb).defaultField("prefixes." + prefix);
                ((QueryStringQueryBuilder)qb).defaultOperator(QueryStringQueryBuilder.Operator.AND);
                ((QueryStringQueryBuilder)qb).autoGeneratePhraseQueries(true);
            }
        }
        return qb;
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

                QueryBuilder qb = buildQbForField(sm.getText1(), sm.getPref1());

                if ( "AND".equals(sm.getOper1()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper1())) {
                    retVal.should(qb);
                }
                if ( "NOT".equals(sm.getOper1()))
                    retVal.mustNot(qb);
            }

            if (sm.getText2() != null && !"".equals(sm.getText2())) {

                QueryBuilder qb = buildQbForField(sm.getText2(), sm.getPref2());

                if ( "AND".equals(sm.getOper1()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper1())) {
                    retVal.should(qb);
                }
                if ( "NOT".equals(sm.getOper1()))
                    retVal.mustNot(qb);
            }

            if (sm.getText3() != null && !"".equals(sm.getText3())) {

                QueryBuilder qb = buildQbForField(sm.getText3(), sm.getPref3());

                if ( "AND".equals(sm.getOper2()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper2()))
                    retVal.should(qb);
                if ( "NOT".equals(sm.getOper2()))
                    retVal.mustNot(qb);
            }

            if (sm.getText4() != null && !"".equals(sm.getText4())) {

                QueryBuilder qb = buildQbForField(sm.getText4(), sm.getPref4());

                if ( "AND".equals(sm.getOper3()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper3()))
                    retVal.should(qb);
                if ( "NOT".equals(sm.getOper3()))
                    retVal.mustNot(qb);
            }

            if (sm.getText5() != null && !"".equals(sm.getText5())) {

                QueryBuilder qb = buildQbForField(sm.getText5(), sm.getPref5());

                if ( "AND".equals(sm.getOper4()))
                    retVal.must(qb);
                if ( "OR".equals(sm.getOper4()))
                    retVal.should(qb);
                if ( "NOT".equals(sm.getOper4()))
                    retVal.mustNot(qb);
            }

            retVal.minimumNumberShouldMatch(1);

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
