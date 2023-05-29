package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.dto.InvUnitSearchDTO;
import org.springframework.data.domain.Page;

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
    Boolean changeRevisionStatuses(InventoryStatus fromStatus, InventoryStatus toStatus, String library,String inventoryId);
    Iterator<InventoryUnit> findAllByInventoryStatusesAndInventoryId(List<String> invStatusesCoderIdList, String inventoryId);
    void removeInventoryIdFromItemAvailabilities(String inventoryId);
    Page<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO, int pageNo, int pageSize);
    List<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO);
}
