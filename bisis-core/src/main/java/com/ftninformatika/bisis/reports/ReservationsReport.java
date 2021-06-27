package com.ftninformatika.bisis.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
public class ReservationsReport {
    HashMap<String, ReservationsGroup> reservationsInQueue;
    HashMap<String, ReservationsGroup> assignedReservations;
}
