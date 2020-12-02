package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryUnitRepository extends MongoRepository<InventoryUnit, String> {
    InventoryUnit findByInventoryIdAndInvNo(String inventoryId, String invNo);
}
