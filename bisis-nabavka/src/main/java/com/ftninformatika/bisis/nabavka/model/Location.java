package com.ftninformatika.bisis.nabavka.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Location {
    private String location;
    private String sublocation;
    private int amount;
}
