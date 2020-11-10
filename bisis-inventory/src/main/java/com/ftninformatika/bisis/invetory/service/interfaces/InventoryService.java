package com.ftninformatika.bisis.invetory.service.interfaces;

import com.ftninformatika.bisis.inventory.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory create(Inventory inventory, String lib);
    Inventory update(Inventory inventory);
    void delete(Inventory inventory);
    Inventory getOne(String _id);
    List<Inventory> getAllForLib(String lib);
}
