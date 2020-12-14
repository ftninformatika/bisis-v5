package com.ftninformatika.bisisris.repositories;

import com.ftninformatika.bisis.circ.CircLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface SubLocationRepository extends MongoRepository<CircLocation,String> {
    @Query("{'library':?0, 'locationCode':?1}")
    public CircLocation findCoder(String library, String locationCode);
}
