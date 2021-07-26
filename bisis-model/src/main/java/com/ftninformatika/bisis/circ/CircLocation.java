package com.ftninformatika.bisis.circ;

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
@Document(collection="coders.circ_location")
public class CircLocation {
    @Id
    private String _id;
    private String library;
    private String locationCode;
    private String description;
    private int lastUserId;
}
