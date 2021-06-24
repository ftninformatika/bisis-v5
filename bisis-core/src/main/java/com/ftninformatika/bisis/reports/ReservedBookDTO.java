package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
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
public class ReservedBookDTO {
    private String recordId;
    private String title;
    private List<String> authors;
    private String publisher;
    private Integer totalCount;

    public static ReservedBookDTO createReservedBookDTO(Book book) {
        ReservedBookDTO reservedBookDTO = new ReservedBookDTO();
        reservedBookDTO.setRecordId(book.get_id());
        reservedBookDTO.setTitle(book.getTitle());
        reservedBookDTO.setAuthors(book.getAuthors());
        reservedBookDTO.setPublisher(book.getPublisher());
        reservedBookDTO.setTotalCount(1);
        return reservedBookDTO;
    }
}
