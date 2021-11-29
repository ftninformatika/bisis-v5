package com.ftninformatika.bisis.mobile;

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
public class ProlongLendingDTO {
    boolean prolongable;
    String message;
    String deadlineDate;
    String resumeDate;
}
