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
public class Signing implements java.io.Serializable, Comparable<Signing> {
    private Date signDate;
    private Date untilDate;
    private String librarian;
    private Double cost;
    private String receipt;
    private String location;

    @Override
    public int compareTo(Signing signing) {
        if (signDate == null) {
            if (signing.getSignDate() == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (signing.getSignDate() == null) {
                return 1;
            } else {
                return signDate.compareTo(signing.getSignDate());
            }
        }
    }
}
