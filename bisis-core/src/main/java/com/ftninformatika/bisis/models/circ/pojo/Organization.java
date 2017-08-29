package com.ftninformatika.bisis.models.circ.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
