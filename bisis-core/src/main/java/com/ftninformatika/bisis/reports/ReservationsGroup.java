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
public class ReservationsGroup {
    String location;
    List<ReservedBook> reservedBooks;

    public ReservationsGroup(){
        this.reservedBooks = new ArrayList<>();
    }
}
