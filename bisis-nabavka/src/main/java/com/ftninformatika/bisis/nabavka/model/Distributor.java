package com.ftninformatika.bisis.nabavka.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_distributors")
@Getter
@Setter
@NoArgsConstructor
public class Distributor implements Serializable {
    @Id
    private String _id;
    private String pib;
    private String name;
    private String phone;
    private String email;
    private String contactPerson;
}
