package com.ftninformatika.bisis.opac2.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Book {
    private String _id;
    private String title;
    private String subtitle;
    private String isbn;
    private String issn;
    private List<String> authors = new ArrayList<>();
    private String imageUrl;
    private String publisher;
    private String pubYear;
}
