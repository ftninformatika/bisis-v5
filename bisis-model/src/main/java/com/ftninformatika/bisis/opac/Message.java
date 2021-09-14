package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_message")
public class Message implements Serializable {
    @Id
    private String _id;
    private String idSender;
    private String idReceiver;
    private String content;
    private Date date;
    private boolean seen;
}
