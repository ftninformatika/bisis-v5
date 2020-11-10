package com.ftninformatika.bisis.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_inventory_units")
public class InventoryUnit {

    private String _id;
    @Indexed(name = "rn_index", direction = IndexDirection.DESCENDING)
    private Integer rn;
    @Indexed(name = "inventory_id_index")
    private String inventoryId;
    @TextIndexed()
    private String invNo;
    private String author;
    private String title;
    private String signature;
    private String publisher;
    private String pubYear;
    @Indexed(name = "inv_status_index")
    private String invStatus;
    @Indexed(name = "revision_status_index")
    private String revisionStatus;
    private Date dateModified;


}
