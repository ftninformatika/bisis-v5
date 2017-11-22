package com.ftninformatika.bisis.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchModelMember extends SearchModel{
    private String prefDate1;
    private String prefDate2;

    Date startDate1;
    Date endDate1;
    Date startDate2;
    Date endDate2;
    String location1;
    String location2;

    public boolean isEmpty(){
        return getText1().equals("") && getText2().equals("") && getText3().equals("")
                && getText4().equals("") && getText5().equals("") && getStartDate1() == null && getStartDate2() == null;
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
        if (pref.equals(prefDate1)){
            return Arrays.asList(startDate1, endDate1);
        }
        if (pref.equals(prefDate2)){
            return Arrays.asList(startDate2, endDate2);
        }
        return null;
    }
}
