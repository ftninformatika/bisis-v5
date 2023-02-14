package com.ftninformatika.bisis.circ;

import com.ftninformatika.bisis.coders.Coder;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 27/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="coders.warning_counter")
public class WarningCounter extends Coder {
    @Id
    private String _id;
    private String warningType;
    private String warnYear;
    private Integer lastNo;

}
