package com.ftninformatika.bisis.opac2.dto;

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
public class ProlongLendingRequestDTO {
    private String email;
    private String lendingId;
}
