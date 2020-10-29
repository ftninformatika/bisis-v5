package com.ftninformatika.bisis.reservations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationOnProfile extends Reservation {
    private String record_id;
    private boolean isBookPickedUp = false;
    private ReservationStatus reservationStatus;
}
