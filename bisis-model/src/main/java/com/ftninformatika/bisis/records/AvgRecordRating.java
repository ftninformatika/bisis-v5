package com.ftninformatika.bisis.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  17.10.19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvgRecordRating {
    private float avgRating;
    private int totalRates;
}
