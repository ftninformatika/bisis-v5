package com.ftninformatika.bisis.reports;

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
public class ReservationsOnLocation {
    List<ReservedBookDTO> reservedBooks;
    String location;
}
