package com.ftninformatika.bisis.inventory;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_inventory")
public class Inventory {
    @Id
    private String _id;
    private String name;
    private Integer year;
    private String library;
    private Date startDate;
    private Date endDate;
    private List<Sublocation> sublocations;
    private List<Location> locations;
    private List<InventoryBook> invBooks;
    //todo status map inv - revision
    private InventoryStatus inventoryStatus;
}
