package com.ftninformatika.bisis.inventory.service.interfaces;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryUnitService {

    InventoryUnit create(InventoryUnit inventory);
    InventoryUnit update(InventoryUnit inventory);
    void delete(InventoryUnit inventory);
    InventoryUnit getOne(String _id);
    List<InventoryUnit> getAllForLib(String lib);
    Page<InventoryUnit> search(String inv_id, Integer pageSize, Integer pageNumber);
}
