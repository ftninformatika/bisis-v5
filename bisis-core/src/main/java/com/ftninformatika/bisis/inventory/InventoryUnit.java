package com.ftninformatika.bisis.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.coders.ItemStatus;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
//    private ItemStatus invStatus; //todo refactor - itemStatus
//    private InventoryStatus revisionStatus; // todo inventoryStatus

    private String itemStatusCoderId;
    private String itemStatusDescription;
    private String inventoryStatusCoderId;
    private String inventoryStatusDescription;
    private Date dateModified;
    private boolean checked;

    public void uncheckInRevision() {
        if (this.inventoryStatusCoderId != null &&
                this.inventoryStatusCoderId.equals(InventoryStatus.IN_REVISION)) {
            this.setChecked(false);
        }
    }
}
