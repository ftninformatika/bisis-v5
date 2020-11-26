package com.ftninformatika.bisis.inventory;

import com.ftninformatika.bisis.coders.Coder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="coders.")
public class InventoryStatusCoder extends Coder {
    @Id private String _id;
}
