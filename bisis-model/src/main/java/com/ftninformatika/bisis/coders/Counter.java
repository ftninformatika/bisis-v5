package com.ftninformatika.bisis.coders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Petar on 1/15/2018.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coders.counters")
public class Counter extends Coder{

    private String _id;
    private String counterName;
    private Integer counterValue;
}
