package com.ftninformatika.bisis.opac2.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for united library collection of
 * books with essential data only
 * @author badf00d21  17.7.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books_common")
public class BookCommon {
    @Id
    private String _id;
    @Indexed(unique = true)
    private Integer uid;
    private String title;
    private String isbn;
    private String issn;
    private String imageUrl;
    private String description;
}
