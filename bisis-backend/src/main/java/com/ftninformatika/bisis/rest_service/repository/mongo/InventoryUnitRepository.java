package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("inventoryUnitBisisRepository")
public interface InventoryUnitRepository extends MongoRepository<InventoryUnit, String> {
    InventoryUnit findByInventoryIdAndInvNo(String inventoryId, String invNo);
}
