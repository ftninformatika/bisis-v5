package com.ftninformatika.bisis.acquisition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Delivery {
    private String title;
    private Date createDate;
    private List<String> acquisitionGroups;

}
