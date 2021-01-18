package com.ftninformatika.bisis.circ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author marijakovacevic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInQueueDTO {
    String userId;
    String firstName;
    String lastName;
    String location;
    String locCoderId;
    String reservationDate;
}
