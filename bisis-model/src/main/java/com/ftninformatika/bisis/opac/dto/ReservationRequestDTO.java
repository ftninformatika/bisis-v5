package com.ftninformatika.bisis.opac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private String recordId;
    private String coderId;
    private String memberNo;
}
