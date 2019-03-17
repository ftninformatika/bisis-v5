package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.coders;

import com.ftninformatika.bisis.coders.Availability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AvailabilityRepository extends MongoRepository<Availability, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Availability> getCoders(String libName);
}
