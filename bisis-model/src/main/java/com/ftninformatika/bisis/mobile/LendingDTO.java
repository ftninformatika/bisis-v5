package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.circ.pojo.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LendingDTO {
    String id;
    String lendingDate;
    String returnDate;
    String deadlineDate;
    String resumeDate;
    String recordId;
    String ctlgNo;
    String title;
    String authors;
    String location;

    public LendingDTO(Report report){
        this.lendingDate = report.getProperty1();
        this.returnDate = report.getProperty2();
        this.authors = report.getProperty3();
        this.title = report.getProperty4();
        this.ctlgNo = report.getProperty5();
        this.recordId = report.getProperty6();
        this.deadlineDate = report.getProperty7();
        this.resumeDate = report.getProperty8();
        this.id = report.getProperty9();
        this.location = report.getProperty10();
    }
}
