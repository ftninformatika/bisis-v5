package com.ftninformatika.bisis.reports;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
public class ReservationsGroup implements Comparable<ReservationsGroup> {
    String location;
    Integer total;
    List<ReservedBook> reservedBooks;

    public ReservationsGroup() {
        this.reservedBooks = new ArrayList<>();
    }

    public void calculateTotal() {
        int total = 0;
        for (ReservedBook rb : this.reservedBooks) {
            total += rb.getTotalCount();
        }
        this.total = total;
    }

    @Override
    public int compareTo(ReservationsGroup rg) {
        return this.location.compareTo(rg.location);
    }
}
