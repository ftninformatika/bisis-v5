package com.ftninformatika.bisis.circ.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dboberic on 28/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Duplicate implements Serializable {
    private Date dupDate;
    private int dupNo;
}
