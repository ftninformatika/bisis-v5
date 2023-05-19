package com.ftninformatika.bisis.coders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 26/07/2017.
 */


@Document(collection="coders.acquisition")
@Getter
@Setter
public class Acquisition extends Coder {
    @Id
    private String _id;
}
