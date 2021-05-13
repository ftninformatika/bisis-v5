package com.ftninformatika.bisis.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  Preview version of ItemAvailability, with updated libDepartment
 */

@Getter
@Setter
@NoArgsConstructor
public class PrimerakPreview {

    private boolean borrowed;
    private String ctlgNo;
    private String recordID;
    private String libDepartment;

    public PrimerakPreview(ItemAvailability availability, String libDepartment) {
        this.borrowed = availability.getBorrowed();
        this.ctlgNo = availability.getCtlgNo();
        this.recordID = availability.getRecordID();
        this.libDepartment = libDepartment;
    }
}
