package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "library")
public class Library implements Serializable {
    @Id
    private String _id;
    private String prefix;
    private String name;
    private String address;
    private String city;
    private String email;
    private String web;
    private String phone;
    private String workingHours;
    private float latitude;
    private float longitude;
}
