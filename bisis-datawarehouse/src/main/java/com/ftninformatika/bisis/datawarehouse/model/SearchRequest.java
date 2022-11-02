package com.ftninformatika.bisis.datawarehouse.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SearchRequest {
    String type;
    LocalDateTime startDate;
    LocalDateTime endDate;
    boolean allData;
    boolean firstMembership;
    String[] lendingAction;
    List<SelectedCoder> coders;

}
