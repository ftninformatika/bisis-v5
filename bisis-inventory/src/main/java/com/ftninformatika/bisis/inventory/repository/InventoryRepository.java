package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.Inventory;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {

    List<Inventory> findAllByLibrary(String library);
    List<Inventory> findAllByInventoryStatusAndLibrary(InventoryStatus inventoryStatus, String library);
}
