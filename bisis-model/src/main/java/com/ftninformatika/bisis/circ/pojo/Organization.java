package com.ftninformatika.bisis.circ.pojo;

import lombok.*;

/**
 * Created by dboberic on 27/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Organization {
    private String id;
    private String name;
    private String address;
    private String city;
    private String zip;

}
