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
@Document(collection="coders.membership")
public class Membership extends Coder {
    @Id
    private String _id;
    private String memberType;
    private String userCateg;
    private Double cost;
}
