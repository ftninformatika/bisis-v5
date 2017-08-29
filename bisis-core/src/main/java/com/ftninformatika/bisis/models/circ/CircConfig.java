package com.ftninformatika.bisis.models.circ;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 28/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="coders.circ_config")
public class CircConfig {
    @Id
    private String _id;
    private String library;
    private String description;
    private String text;

    private String circOptionsXML;
    private String validatorOptionsXML;
}
