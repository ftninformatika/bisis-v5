package com.ftninformatika.bisis.datawarehouse.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SearchRequest {
    String type;
    LocalDateTime startDate;
    LocalDateTime endDate;
    boolean allData;
    boolean firstMembership;
    String[] lendingAction;
    List<SelectedCoder> coders;

    @Override
    public String toString(){
        List list = coders.stream().map(x->x.toString()).collect(Collectors.toList());
        return String.join("; ", list);
    }

}
