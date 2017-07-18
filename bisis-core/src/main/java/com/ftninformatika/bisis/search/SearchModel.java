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

    //nazivi odabranih prefiksa
    private String pref1;
    private String pref2;
    private String pref3;
    private String pref4;
    private String pref5;
    //vrednosti za pretragu odgovarajucih prefiksa
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    //operatori koji povezuju polja (AND, OR, NOT)
    private String oper1;
    private String oper2;
    private String oper3;
    private String oper4;
    //sortiranje
    private String sort;

}
