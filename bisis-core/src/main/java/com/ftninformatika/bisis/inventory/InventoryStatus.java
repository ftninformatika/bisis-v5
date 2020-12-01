package com.ftninformatika.bisis.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.coders.Coder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="coders.inventory_status")
public class InventoryStatus extends Coder {
    @Id private String _id;

    @JsonIgnore public static final String IN_REVISION = "2";
    @JsonIgnore public static final String ON_PLACE = "1";
}
