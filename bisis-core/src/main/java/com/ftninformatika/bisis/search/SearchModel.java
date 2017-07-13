package com.ftninformatika.bisis.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
/**
 * Created by Petar on 7/13/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {

    private String pref1;
    private String pref2;
    private String pref3;
    private String pref4;
    private String pref5;
    private String oper1;
    private String oper2;
    private String oper3;
    private String oper4;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String sort;


    public QueryBuilder makeQuery(){
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();


        try {
            if (!"".equals(text1))
                retVal.must(QueryBuilders.matchPhrasePrefixQuery(pref1, text1));

            if (!"".equals(text2)) {
                if ( "AND".equals(oper1))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery(pref2, text2));
                if ( "OR".equals(oper1)) {
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery(pref2, text2));
                }
                if ( "NOT".equals(oper1))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery(pref2, text2));
            }

            if (!"".equals(text3)) {
                if ( "AND".equals(oper2))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery(pref3, text3));
                if ( "OR".equals(oper2))
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery(pref3, text3));
                if ( "NOT".equals(oper2))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery(pref3, text3));
            }

            if (!text4.equals("")) {
            } else {
                if ( "AND".equals(oper3))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery(pref4, text4));
                if ( "OR".equals(oper3))
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery(pref4, text4));
                if ( "NOT".equals(oper3))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery(pref4, text4));
            }

            if (!"".equals(text5)) {
                if ( "AND".equals(oper4))
                    retVal.must(QueryBuilders.matchPhrasePrefixQuery(pref5, text5));
                if ( "OR".equals(oper4))
                    retVal.should(QueryBuilders.matchPhrasePrefixQuery(pref5, text5));
                if ( "NOT".equals(oper4))
                    retVal.mustNot(QueryBuilders.matchPhrasePrefixQuery(pref5, text5));
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }



        return retVal;
    }
}
