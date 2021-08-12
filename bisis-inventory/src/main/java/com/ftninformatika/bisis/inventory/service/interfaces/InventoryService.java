package com.ftninformatika.bisis.inventory.service.interfaces;

import com.ftninformatika.bisis.inventory.Inventory;

import java.util.Date;
import java.util.List;

public interface InventoryService {

    Inventory create(Inventory inventory, String lib);
    Inventory update(Inventory inventory);
    void delete(Inventory inventory);
    Inventory getOne(String _id);
    List<Inventory> getAllForLib(String lib);
    List<Inventory> getAllForLibAndLocations(String lib, List<String> locations);
    Boolean updateLendingStatus(String  inventoryId);
    Boolean updateLendingStatusFix(String inventoryId, Date revisionStart, boolean takeAll);
    Boolean hasGeneratingInventoryForLib(String library);
}
