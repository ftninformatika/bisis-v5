package com.ftninformatika.bisis.coders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 29.3.2018..
 */
@Getter
@Setter
@Document(collection="coders.task")
public class Task extends Coder {
    @Id
    private String _id;

}
