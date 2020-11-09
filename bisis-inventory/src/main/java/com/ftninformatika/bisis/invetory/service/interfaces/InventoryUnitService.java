package com.ftninformatika.bisis.invetory.service.interfaces;

import com.ftninformatika.bisis.inventory.InventoryUnit;

import java.util.List;

public interface InventoryUnitService {

    InventoryUnit create(InventoryUnit inventory);
    InventoryUnit update(InventoryUnit inventory);
    void delete(InventoryUnit inventory);
    InventoryUnit getOne(String _id);
    List<InventoryUnit> getAllForLib(String lib);
}
