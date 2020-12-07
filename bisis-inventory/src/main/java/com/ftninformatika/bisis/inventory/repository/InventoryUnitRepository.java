package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryUnitRepository extends MongoRepository<InventoryUnit, String>,
        PagingAndSortingRepository<InventoryUnit, String>, InventoryUnitAdditionalRepository {

    void deleteAllByInventoryId(String inventoryId);
    Page<InventoryUnit> findByInventoryId(String inventory_id, Pageable pageable);
    InventoryUnit findByInventoryIdAndInvNo(String inventoryId, String invNo);
    Double countAllByInventoryId(String inventoryId);
    Double countByInventoryIdAndCheckedIsTrue(String inventoryUnit);

}
