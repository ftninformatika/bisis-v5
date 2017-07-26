package com.ftninformatika.bisis.coders;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 25/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="coders.location")
public class Location {
    @Id
    private String _id;
    private String library;
    private String location_id;
    private String location_name;
}
