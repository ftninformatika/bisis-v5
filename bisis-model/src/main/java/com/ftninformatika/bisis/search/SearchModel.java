package com.ftninformatika.bisis.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private String oper5;
    //sortiranje
    private String sort;

    private List<String> departments = new ArrayList<>();
    private List<String> branches = new ArrayList<>();

    @Override
    public String toString(){
        StringBuffer retVal = new StringBuffer();

        if (!"".equals(text1))
            retVal.append(pref1 + ":" + text1);
        if (!"".equals(text1) && !"".equals(text2))
            retVal.append(" " + oper1 + " ");
        if (!"".equals(text2))
            retVal.append(pref2 + ":" + text2);
        if (!"".equals(text2) && !"".equals(text3))
            retVal.append(" " + oper2 + " ");
        if (!"".equals(text3))
            retVal.append(pref3 + ":" + text3);
        if (!"".equals(text3) && !"".equals(text4))
            retVal.append(" " + oper3 + " ");
        if (!"".equals(text4))
            retVal.append(pref4 + ":" + text4);
        if (!"".equals(text4) && !"".equals(text5))
            retVal.append(" " + oper4 + " ");
        if (!"".equals(text5))
            retVal.append(pref5 + ":" + text5);

        return retVal.toString();
    }

    public Object getValueForPrefix(String pref){
        if (pref.equals(getPref1())){
            return getText1();
        }
        if (pref.equals(getPref2())){
            return getText2();
        }
        if (pref.equals(getPref3())){
            return getText3();
        }
        if (pref.equals(getPref4())){
            return getText4();
        }
        if (pref.equals(getPref5())){
            return getText5();
        }
        return null;
    }

    public void setValueForPrefix(String pref,String value){
        if (pref.equals(getPref1())){
            setText1(value);
        }
        if (pref.equals(getPref2())){
            setText2(value);
        }
        if (pref.equals(getPref3())){
            setText3(value);
        }
        if (pref.equals(getPref4())){
            setText4(value);
        }
        if (pref.equals(getPref5())){
            setText5(value);
        }
    }
}
