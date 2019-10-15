package com.ftninformatika.bisis.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  15.10.19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordRating {
    private String username;
    private String libraryMemberId;
    private Long givenRating;
}
