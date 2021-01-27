package com.ftninformatika.bisis.nabavka.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_desideratum")
@Getter
@Setter
@NoArgsConstructor
public class Desideratum {

    @Id
    private String _id;
    private String isbn;
    private String author;
    private String title;
    private String publisher;
    private List<Location> locations;
}
