package com.ftninformatika.bisis.acquisition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_offers")
@Getter
@Setter
@NoArgsConstructor
public class Offer implements Serializable {
    @Id
    private String _id;
    private String distributor;
    private List<Book> items;
}
