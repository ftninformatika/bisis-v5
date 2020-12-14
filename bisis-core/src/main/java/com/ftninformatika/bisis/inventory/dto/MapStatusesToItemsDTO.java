package com.ftninformatika.bisis.inventory.dto;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapStatusesToItemsDTO {
    private String inventoryId;
    private List<StatusMappingEntry> statusMapEntryList;

    public StatusMappingEntry getEntryByInventoryStatus(InventoryStatus inventoryStatus) {
        if (statusMapEntryList == null || statusMapEntryList.size() == 0 || inventoryStatus == null || inventoryStatus.getCoder_id() == null) {
            return null;
        }
        for (StatusMappingEntry entry: statusMapEntryList) {
            if (entry.getInventoryStatusCoderId().equals(inventoryStatus.getCoder_id())) {
                return entry;
            }
        }
        return null;
    }

//    public String getItemStatusCoderIdForRevStatus(InventoryStatus inventoryStatus) {
//        if (statusMapEntryList == null || statusMapEntryList.size() == 0 || inventoryStatus == null || inventoryStatus.getCoder_id() == null) {
//            return null;
//        }
//        for (StatusMappingEntry entry: statusMapEntryList) {
//            if (entry.getInventoryStatusCoderId().equals(inventoryStatus.getCoder_id())) {
//                return entry.getItemStatusCoderId();
//            }
//        }
//        return null;
//    }
}
