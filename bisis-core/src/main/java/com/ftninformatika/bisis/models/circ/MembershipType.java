package com.ftninformatika.bisis.models.circ;

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
@Document(collection="coders.membership_type")
public class MembershipType {
    @Id
    private String _id;
    private String library;
    private String description;
    private int period;
}
