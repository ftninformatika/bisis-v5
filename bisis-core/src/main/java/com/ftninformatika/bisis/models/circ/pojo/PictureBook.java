package com.ftninformatika.bisis.models.circ.pojo;

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
public class PictureBook implements Serializable {
    private Date lendDate;
    private int lendNo;
    private int returnNo;
    private int status;
}
