package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.circ.CircLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Petar on 8/30/2017.
 */
public interface CircLocationRepository extends MongoRepository<CircLocation, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<CircLocation> getCoders(String libName);
}
