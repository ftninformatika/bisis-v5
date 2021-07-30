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
        this.recordId = book.get_id();
        this.rn = rn;
        this.title = book.getTitle();
        this.authors = book.getAuthors();
        this.publisher = book.getPublisher();
        this.totalCount = 1;
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
