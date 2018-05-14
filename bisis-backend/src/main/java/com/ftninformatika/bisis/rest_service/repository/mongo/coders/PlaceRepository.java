package com.ftninformatika.bisis.rest_service.repository.mongo.coders;

import com.ftninformatika.bisis.circ.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlaceRepository extends MongoRepository<Place, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Place> getCoders(String libName);
}
