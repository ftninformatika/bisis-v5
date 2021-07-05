package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.reservations.Reservation;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
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
public class ReservedBook implements Comparable<ReservedBook> {
    private String recordId;
    private int rn;
    private String title;
    private List<String> authors;
    private String publisher;
    private Integer totalCount;
    private String ctlgNo;
    private String userId;

    public ReservedBook(Book book, int rn) {
        ReservedBook reservedBook = new ReservedBook();
        reservedBook.setRecordId(book.get_id());
        reservedBook.setRn(rn);
        reservedBook.setTitle(book.getTitle());
        reservedBook.setAuthors(book.getAuthors());
        reservedBook.setPublisher(book.getPublisher());
        reservedBook.setTotalCount(1);
    }

    public void setAdditionalData(Member member, Reservation reservation) {
        if (member != null) {
            this.setCtlgNo(((ReservationOnProfile) reservation).getCtlgNo());
            this.setUserId(member.getUserId());
        }
    }

    @Override
    public int compareTo(ReservedBook rb) {
        return rb.totalCount.compareTo(this.totalCount);
    }
}
