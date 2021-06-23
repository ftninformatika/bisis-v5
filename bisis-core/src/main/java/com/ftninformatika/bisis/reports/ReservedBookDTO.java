package com.ftninformatika.bisis.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
public class ReservedBookDTO {
    private String recordId;
    private String title;
    private List<String> authors;
    private String publisher;
    private Integer totalCount;
}
