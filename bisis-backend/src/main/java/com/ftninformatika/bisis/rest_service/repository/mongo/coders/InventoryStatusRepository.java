package com.ftninformatika.bisis.rest_service.repository.mongo.coders;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InventoryStatusRepository extends MongoRepository<InventoryStatus, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<InventoryStatus> getCoders(String libName);

    @Query("{'coder_id': ?0}")
    InventoryStatus getByCoder_Id(String coder_id);

}
