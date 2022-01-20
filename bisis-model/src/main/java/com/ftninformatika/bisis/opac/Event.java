package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_event")
public class Event implements Serializable {
    @Id
    private String _id;
    private Date date;
    private String content;
    private String title;
    private String location;
    private String link;
    private String linkName;
}
