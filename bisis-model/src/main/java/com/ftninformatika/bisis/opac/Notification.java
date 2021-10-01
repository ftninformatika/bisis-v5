package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Getter
@Setter
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_notification")
public class Notification {
    @Id
    String id;
    String title;
    String content;
    String type;
    String sender;
    Date sentDate;
}
