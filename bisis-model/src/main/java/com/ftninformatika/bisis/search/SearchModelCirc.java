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
public class SearchModelCirc extends SearchModel{

    Date startDateLend;
    Date endDateLend;
    Date startDateRet;
    Date endDateRet;
    String location;

}
