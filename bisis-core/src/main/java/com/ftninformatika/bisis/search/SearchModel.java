package com.ftninformatika.bisis.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.index.query.QueryBuilder;

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
        QueryBuilder retVal = null;

        

        return retVal;
    }
}
