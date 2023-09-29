package com.ftninformatika.bisis.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.common.util.set.Sets;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchModelMember extends SearchModel{
    /**
     * To escape regex special characters
     */
    public static Set<String> PREDEFINED_VALUE_PREFIXES = Sets.newHashSet("educationLevel", "membershipType.description",
            "corporateMember.instName","language", "userCategory.description", "organization.description");
    private String prefDate1;
    private String prefDate2;

    Date startDate1;
    Date endDate1;
    Date startDate2;
    Date endDate2;
    String location1;
    String location2;

    public boolean isEmpty(){
        return getText1() == null && getText2() == null && getText3() == null
                && getText4() == null && getText5() == null && getStartDate1() == null && getStartDate2() == null;
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
            Object[] arr = new Object[3];
            arr[0]=startDate1;
            arr[1]=endDate1;
            arr[2]=location1;
            return arr;
        }
        if (pref.equals(prefDate2)){
            Object[] arr = new Object[3];
            arr[0]=startDate2;
            arr[1]=endDate2;
            arr[2]=location2;
        }
        return null;
    }
}
