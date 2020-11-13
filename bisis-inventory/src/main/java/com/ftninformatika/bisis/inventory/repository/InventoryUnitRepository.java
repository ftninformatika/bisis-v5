package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryUnitRepository extends MongoRepository<InventoryUnit, String>,
        PagingAndSortingRepository<InventoryUnit, String> {

    Page<InventoryUnit> findByInventoryId(String inventory_id, Pageable pageable);
//    Page<InventoryUnit> findByInventoryId(String inventory_id);
}
