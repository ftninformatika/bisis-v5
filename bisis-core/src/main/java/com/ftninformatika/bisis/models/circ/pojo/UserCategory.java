package com.ftninformatika.bisis.models.circ.pojo;

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
public class UserCategory {
    private String description;
    private int titles_no;
    private int period;
    private int max_period;
}
