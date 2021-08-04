package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.records.AvgRecordRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    String _id;
    List<String> authors;
    List<String> otherAuthors;
    String title;
    String subtitle;
    String publisher;
    String publisherYear;
    String publisherPlace;
    AvgRecordRating avgRating;
    String imageUrl;
    String description;

    public BookDTO(Book book) {
        this._id = book.get_id();
        this.authors = book.getAuthors();
        this.otherAuthors = book.getOtherAuthors();
        this.title = book.getTitle();
        this.subtitle = book.getSubtitle();
        this.publisher = book.getPublisher();
        this.publisherYear = book.getPublishYear();
        this.publisherPlace = book.getPublishPlace();
        this.avgRating = book.getAvgRating();
        this.imageUrl = book.getImageUrl();
        this.description = book.getDescription();
    }
}
