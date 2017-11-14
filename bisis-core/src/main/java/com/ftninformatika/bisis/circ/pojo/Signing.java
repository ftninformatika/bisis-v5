package com.ftninformatika.bisis.circ.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by dboberic on 28/07/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Signing implements java.io.Serializable {
    private Date signDate;
    private Date untilDate;
    private String librarian;
    private Double cost;
    private String receipt;
    private String location;
}
