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
public class CorporateMember implements Serializable {
    private String userId;
    private String instName;
    private Date signDate;
    private String address;
    private String city;
    private Integer zip;
    private String phone;
    private String email;
    private String fax;
    private String secAddress;
    private Integer secZip;
    private String secCity;
    private String secPhone;
    private String contFirstName;
    private String contLastName;
    private String contEmail;
}
