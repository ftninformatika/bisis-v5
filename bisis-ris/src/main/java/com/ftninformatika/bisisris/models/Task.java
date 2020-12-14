package com.ftninformatika.bisisris.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_task")
@Getter
@Setter
public class Task {
    @Id
    String _id;
    String librarian;
    String location;
    String sublocation;
    String serviceType;
    int serviceTypeCode;
    String method;
    Date dateOfService;
    int quantity;

}
