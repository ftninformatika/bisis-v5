package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;

import java.util.Iterator;
import java.util.List;

public interface InventoryUnitAdditionalRepository {

    /**
     * Put index on all searcheable fields
     */
    void indexFields();

    /**
     *
     * Updates revision statuses from - to
     */
    Boolean changeRevisionStatuses(InventoryStatus fromStatus, InventoryStatus toStatus, String library);
    Iterator<InventoryUnit> findAllByInventoryStatusesAndInventoryId(List<String> invStatusesCoderIdList, String inventoryId);
    void removeInventoryIdFromItemAvailabilities(String inventoryId);
}
