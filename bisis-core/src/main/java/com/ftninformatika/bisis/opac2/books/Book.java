package com.ftninformatika.bisis.opac2.books;

import com.ftninformatika.bisis.records.AvgRecordRating;
import com.ftninformatika.bisis.records.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author badf00d21  29.7.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    //    Record mongoId
    private String _id;
    private Integer pubType;
    private List<String> authors = new ArrayList<>();
    private List<String> otherAuthors = new ArrayList<>();
    private String title;
    private String subtitle;
    private String publisher;
    private String publishYear;
    private String publishPlace;
    private String isbn;
    private String issn;
    private String pagesCount;
    private String dimensions;
    private Record record = null;
    private String udk;
    private String notes;
    private String isbdHtml;
    private List<Item> items = null;
    private int totalRatings;
    private AvgRecordRating avgRating;
//    Data from books_common collection
    private String imageUrl;
    private String description;
    private Integer commonBookUID;
}
