package com.ftninformatika.bisis.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
public class ReservationsReport {
    Collection<ReservationsGroup> reservationsInQueue;
    Collection<ReservationsGroup> assignedReservations;
    Collection<ReservationsGroup> pickedUpReservations;
}
