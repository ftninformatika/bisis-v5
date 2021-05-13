package com.ftninformatika.bisis.location.dto;

import com.ftninformatika.bisis.records.Record;
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
public class RecordCtlgNoDTO {
    Record record;
    String ctlgNo;
}
