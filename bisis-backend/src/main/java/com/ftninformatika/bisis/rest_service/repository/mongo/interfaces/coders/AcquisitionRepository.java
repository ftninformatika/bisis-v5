package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.coders;

import com.ftninformatika.bisis.coders.Acquisition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AcquisitionRepository extends MongoRepository<Acquisition, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Acquisition> getCoders(String libName);
}
