package com.ftninformatika.bisis.circ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author marijakovacevic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationsRequestDTO {
    String userId;
    String coderId;
    List<String> ctlgNos;
}
