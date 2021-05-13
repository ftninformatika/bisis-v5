package com.ftninformatika.bisis.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.coders.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_inventory")
public class Inventory {
    @Id
    private String _id;
    private String name;
    private String library;
    private Date startDate;
    private Date endDate;
    private List<Coder> itemStatuses;
    private List<Coder> invLocations;
    private List<InventoryBook> invBooks;
    private List<InventoryStatusPair> invToRevisionStatuses;
    private EnumInventoryState inventoryState;
    private EnumActionState currentAction = EnumActionState.NONE;
    private Double progress;
    private Integer numberOfInvUnits;
    private Integer numberOfNotActiveInvUnits;

    @Transient @JsonIgnore Map<String, ItemStatus> itemStatusesMap;
    @Transient @JsonIgnore Map<String, InventoryStatus> inventoryStatusesMap;

    public ItemStatus getItemStatus(String key) {
        return itemStatusesMap.get(key);
    }

    public InventoryStatus getRevStatusByInv(String invStatus) {
        InventoryStatus retVal = inventoryStatusesMap.get(InventoryStatus.IN_REVISION);
        if (invToRevisionStatuses == null || invStatus == null) {
            return retVal;
        }
        for (InventoryStatusPair pair: invToRevisionStatuses) {
            if (pair.getFromStatusCoder().equals(invStatus)) {
                retVal = inventoryStatusesMap.get(pair.getToStatusCoder());
            }
        }
        return retVal;
    }
}
