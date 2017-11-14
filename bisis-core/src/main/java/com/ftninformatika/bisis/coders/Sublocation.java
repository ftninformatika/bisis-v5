package com.ftninformatika.bisis.coders;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 26/07/2017.
 */


@Document(collection="coders.sublocation")
public class Sublocation extends Coder{
    @Id
    private String _id;
}
