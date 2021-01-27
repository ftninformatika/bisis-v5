package com.ftninformatika.bisis.nabavka.model;

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
