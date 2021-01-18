package com.ftninformatika.bisisris.repositories;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CircLocationRepository extends MongoRepository<CircLocation, String> {
    @Query("{'library':?0, 'locationCode':?1}")
    public CircLocation findCoder(String library, String coder_id);
}
