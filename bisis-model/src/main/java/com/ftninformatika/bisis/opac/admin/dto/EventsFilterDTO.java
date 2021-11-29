package com.ftninformatika.bisis.opac.admin.dto;

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
public class EventsFilterDTO {
    private Date from;
    private Date to;
    private String searchText;
    private String sortValue;
    private Boolean ascending;
}
