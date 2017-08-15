package com.ftninformatika.bisis.models.circ.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dboberic on 28/07/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Warning implements Serializable {
    private Date warningDate;
    private String warningType;
    private String warnNo;
    private Date deadline;
    private String note;

}
