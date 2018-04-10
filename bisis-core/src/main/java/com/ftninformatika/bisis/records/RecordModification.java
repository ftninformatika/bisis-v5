package com.ftninformatika.bisis.records;
/**
 * Author Petar
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * Class for memorizing modifications on the record
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordModification {

    /**Username of librarian*/
    private String librarianUsername;
    /**Date of modification*/
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'")
    private Date dateOfModification;
}
