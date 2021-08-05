package com.ftninformatika.bisis.opac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author marijakovacevic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProlongLendingResponseDTO {
    private boolean prolongable;
    private String message;
    private Date deadline;
    private Date resume;
}
