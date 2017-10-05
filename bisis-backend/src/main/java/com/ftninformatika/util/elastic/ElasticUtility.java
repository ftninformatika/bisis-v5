package com.ftninformatika.util.elastic;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.search.SearchModel;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Petar on 7/17/2017.
 */
public class ElasticUtility {

    public static ArrayList<String> getIdsFromElasticIterable(Iterable<ElasticPrefixEntity> elasticResponse){
        ArrayList<String> retVal = new ArrayList<>();
        for (ElasticPrefixEntity ee: elasticResponse ){
            retVal.add(ee.getId());
        }
        return retVal;
    }

    public static BoolQueryBuilder searchByAuthorQuery(String author){
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();
        retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes.AU", author));
        return retVal;
    }

    public static BoolQueryBuilder searchByTitleQuery(String title){
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();
        retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes.TI", title));
        return retVal;
    }

    public static BoolQueryBuilder searchByKeywordQuery(String kw){
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();
        retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes.KW", kw));
        return retVal;
    }

    //formiranje Query-ja za glavnu pretragu zapisa
    public static BoolQueryBuilder makeQuery(SearchModel sm){
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();

        retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref1(), sm.getText1()));

        try {
            if (sm.getText1() != null && !"".equals(sm.getText1()))
                retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref1(), sm.getText1()));

            if (sm.getText2() != null && !"".equals(sm.getText2())) {
                if ( "AND".equals(sm.getOper1()))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref2(), sm.getText2()));
                if ( "OR".equals(sm.getOper1())) {
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref1(), sm.getText2()));
                }
                if ( "NOT".equals(sm.getOper1()))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref2(), sm.getText2()));
            }

            if (sm.getText3() != null && !"".equals(sm.getText3())) {
                if ( "AND".equals(sm.getOper2()))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref3(), sm.getText3()));
                if ( "OR".equals(sm.getOper2()))
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref3(), sm.getText3()));
                if ( "NOT".equals(sm.getOper2()))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref3(), sm.getText3()));
            }

            if (sm.getText4() != null && !"".equals(sm.getText4())) {

                if ( "AND".equals(sm.getOper3()))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref4(), sm.getText4()));
                if ( "OR".equals(sm.getOper3()))
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref4(), sm.getText4()));
                if ( "NOT".equals(sm.getOper3()))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref4(), sm.getText4()));
            }

            if (sm.getText5() != null && !"".equals(sm.getText5())) {
                if ( "AND".equals(sm.getOper4()))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref5(), sm.getText5()));
                if ( "OR".equals(sm.getOper4()))
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref5(), sm.getText5()));
                if ( "NOT".equals(sm.getOper4()))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery("prefixes." + sm.getPref5(), sm.getText5()));
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

            retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes."+prefName, prefValue));
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        return retVal;
    }

}
