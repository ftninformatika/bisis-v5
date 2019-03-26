package com.ftninformatika.bisis.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  26.3.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordBriefs {
    private String _id;
    private String autor;
    private String title;
    private String publisher;
    private String publicYear;
    private String language;
    private String country;
}
