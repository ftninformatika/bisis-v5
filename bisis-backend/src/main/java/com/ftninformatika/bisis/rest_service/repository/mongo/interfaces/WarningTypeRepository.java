package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces;

import com.ftninformatika.bisis.circ.WarningType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WarningTypeRepository extends MongoRepository<WarningType, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<WarningType> getCoders(String libName);
}
