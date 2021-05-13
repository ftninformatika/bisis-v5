package com.ftninformatika.bisis.circ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmReservationDTO {
    private String reservation_id;
    private String record_id;
    private String ctlgNo;
}
