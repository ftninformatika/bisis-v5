package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.opac2.books.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
public class ReservedBook implements Comparable<ReservedBook>{
    private String recordId;
    private String title;
    private List<String> authors;
    private String publisher;
    private Integer totalCount;

    public static ReservedBook createReservedBookDTO(Book book) {
        ReservedBook reservedBook = new ReservedBook();
        reservedBook.setRecordId(book.get_id());
        reservedBook.setTitle(book.getTitle());
        reservedBook.setAuthors(book.getAuthors());
        reservedBook.setPublisher(book.getPublisher());
        reservedBook.setTotalCount(1);
        return reservedBook;
    }

    @Override
    public int compareTo(ReservedBook rb) {
        return rb.totalCount.compareTo(this.totalCount);
    }
}
