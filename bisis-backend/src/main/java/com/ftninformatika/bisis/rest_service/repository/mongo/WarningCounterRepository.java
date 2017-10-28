package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.circ.WarningCounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WarningCounterRepository extends MongoRepository<WarningCounter, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<WarningCounter> getCoders(String libName);
}
