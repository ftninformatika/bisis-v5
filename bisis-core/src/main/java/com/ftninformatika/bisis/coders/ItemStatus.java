package com.ftninformatika.bisis.coders;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="coders.status")
public class ItemStatus extends Coder {
    @Id private String _id;
    private boolean lendable;
    private boolean showable;
}
