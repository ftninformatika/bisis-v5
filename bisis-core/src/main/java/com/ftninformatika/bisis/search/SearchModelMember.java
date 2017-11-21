package com.ftninformatika.bisis.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
