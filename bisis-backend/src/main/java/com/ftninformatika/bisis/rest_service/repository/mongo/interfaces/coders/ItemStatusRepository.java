package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.coders;

import com.ftninformatika.bisis.coders.ItemStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemStatusRepository extends MongoRepository<ItemStatus, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<ItemStatus> getCoders(String libName);
}
