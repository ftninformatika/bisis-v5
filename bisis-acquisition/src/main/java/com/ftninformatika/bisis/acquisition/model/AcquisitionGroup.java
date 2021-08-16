package com.ftninformatika.bisis.acquisition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AcquisitionGroup {
    private String title;
    private String distributor;
    private List<Item> items;
}
