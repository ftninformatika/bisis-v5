package com.ftninformatika.bisis.circ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmReservationDTO {
    private String reservation_id;
    private String record_id;
    private Date reservationDate;
    private String locationCode;
}
