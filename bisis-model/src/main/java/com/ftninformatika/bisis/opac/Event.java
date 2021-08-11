package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_event")
public class Event implements Serializable {
    @Id
    private String _id;
    private LocalDateTime date;
    private String content;
    private String title;
}
