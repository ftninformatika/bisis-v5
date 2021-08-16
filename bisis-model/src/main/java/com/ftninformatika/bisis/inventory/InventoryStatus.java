package com.ftninformatika.bisis.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.coders.Coder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="coders.inventory_status")
@Getter
@Setter
public class InventoryStatus extends Coder {
    @Id private String _id;

    @JsonIgnore public static final String IN_REVISION = "2";
    @JsonIgnore public static final String IN_REVISION_DESC = "U reviziji";
    @JsonIgnore public static final String ON_PLACE = "1";
    @JsonIgnore public static final String ON_PLACE_DESC = "Na mestu";
    @JsonIgnore public static final String ON_LENDING = "3";
    @JsonIgnore public static final String ON_LENDING_L2 = "5";
    @JsonIgnore public static final String SPENT_OLD_INVENTORY = "8"; // RASHODOVANO PRETHODNIM REVIZIJAMA
}
