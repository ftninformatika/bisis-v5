package com.ftninformatika.bisis.models.circ.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dboberic on 28/07/2017.
 */
public class Warning implements Serializable {
    private Date warningDate;
    private String warningType;
    private String warnNo;
    private Date deadline;
    private String note;

}
