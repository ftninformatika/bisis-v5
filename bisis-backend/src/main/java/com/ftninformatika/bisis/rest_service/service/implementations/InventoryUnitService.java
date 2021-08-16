package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.rest_service.repository.mongo.InventoryUnitRepository;
import com.ftninformatika.bisis.core.repositories.InventoryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("inventoryUnitBisisService")
public class InventoryUnitService {
    @Autowired
    @Qualifier("inventoryUnitBisisRepository")
    private InventoryUnitRepository inventoryUnitRepository;
    @Autowired
    private InventoryStatusRepository inventoryStatusRepository;

    public InventoryUnit setOnPlace(String inventoryId, String invNo, String library) {
        if (inventoryId == null || invNo == null) {
            return null;
        }
        InventoryUnit inventoryUnit = inventoryUnitRepository.findByInventoryIdAndInvNo(inventoryId, invNo);
        InventoryStatus onPlaceStatus = inventoryStatusRepository.getByCoder_Id(InventoryStatus.ON_PLACE);
        if (onPlaceStatus == null || inventoryUnit == null) {
            return null; //todo logger
        }
        inventoryUnit.setInventoryStatusCoderId(onPlaceStatus.getCoder_id());
        inventoryUnit.setInventoryStatusDescription(onPlaceStatus.getDescription());
        inventoryUnit.setChecked(true);
        return inventoryUnitRepository.save(inventoryUnit);
    }
}
