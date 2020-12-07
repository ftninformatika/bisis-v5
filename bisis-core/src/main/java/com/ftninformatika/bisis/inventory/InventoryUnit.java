package com.ftninformatika.bisis.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.coders.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_inventory_units")
public class InventoryUnit {

    private String _id;
    private Integer rn;
    private String inventoryId;
    private String invNo;
    private String author;
    private String title;
    private String signature;
    private String publisher;
    private String pubYear;
    private String status;
    private ItemStatus invStatus;
    private InventoryStatus revisionStatus;
    private Date dateModified;
    private boolean checked;

}
