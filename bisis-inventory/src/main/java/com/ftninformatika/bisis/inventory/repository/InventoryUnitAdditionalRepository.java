package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryStatus;

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
}
