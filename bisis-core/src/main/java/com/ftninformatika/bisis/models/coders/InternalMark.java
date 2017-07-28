package com.ftninformatika.bisis.models.coders;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 26/07/2017.
 */

@Document(collection="coders.internalMark")
public class InternalMark extends Coder{
    @Id
    private String _id;
}
