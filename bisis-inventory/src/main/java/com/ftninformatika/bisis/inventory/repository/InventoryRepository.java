package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.Inventory;
import com.ftninformatika.bisis.inventory.EnumInventoryState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {

    List<Inventory> findAllByLibrary(String library);
    List<Inventory> findAllByInventoryStateAndLibrary(EnumInventoryState inventoryState, String library);
    Integer countAllByInventoryStateAndLibrary(EnumInventoryState inventoryState, String library);
}
