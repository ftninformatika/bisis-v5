package com.ftninformatika.bisis.circ.pojo;

import lombok.*;

/**
 * Created by dboberic on 27/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCategory {
    private String description;
    private int titlesNo;
    private int period;
    private int maxPeriod;
}
