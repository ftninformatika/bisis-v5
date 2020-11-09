package com.ftninformatika.bisis.invetory.repository;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryUnitRepository extends MongoRepository<InventoryUnit, String>, PagingAndSortingRepository<InventoryUnit, String> {
}
