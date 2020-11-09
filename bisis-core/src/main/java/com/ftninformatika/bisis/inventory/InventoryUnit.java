package com.ftninformatika.bisis.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_inventory_units")
public class InventoryUnit {

    private String _id;
    @Indexed
    private Integer rn;
    @Indexed
    private String inventory_id;
    @Indexed
    private String invNo;
    private String author;
    private String title;
    private String signature;
    private String publisher;
    private String pubYear;
    @Indexed
    private String invStatus;
    @Indexed
    private String revisionStatus;
    private Date dateModified;


}
