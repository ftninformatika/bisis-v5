package com.ftninformatika.bisis.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
public class ReservationsReport {
    HashMap<String, List<ReservedBookDTO>> reservationsInQueue;
    HashMap<String, List<ReservedBookDTO>> assignedReservations;
}
