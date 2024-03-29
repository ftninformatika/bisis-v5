package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_faq")
public class FAQ implements Serializable {
    @Id
    private String _id;
    private String question;
    private String answer;
    private Integer index;
}
