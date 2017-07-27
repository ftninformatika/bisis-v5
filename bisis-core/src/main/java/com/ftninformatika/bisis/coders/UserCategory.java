package com.ftninformatika.bisis.coders;

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
@Document(collection="coders.user_categ")
public class UserCategory {
    @Id
    private String _id;
    private String library;
    private String description;
    private int titles_no;
    private int period;
    private int max_period;
}
