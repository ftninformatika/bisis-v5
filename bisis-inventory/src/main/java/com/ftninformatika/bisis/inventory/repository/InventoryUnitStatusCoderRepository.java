package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.coders.InventoryUnitStatusCoder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryUnitStatusCoderRepository extends MongoRepository<InventoryUnitStatusCoder, String> {
    List<InventoryUnitStatusCoder> findAllByLibraryIsNullOrLibrary(String library);
}
