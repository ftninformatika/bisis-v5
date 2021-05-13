package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.CircLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CircLocationRepository extends MongoRepository<CircLocation, String> {
    @Query("{'library':?0, 'locationCode':?1}")
    public CircLocation findCoder(String library, String coder_id);

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<CircLocation> getCoders(String libName);

    public List<CircLocation> findByLocationCodeAndLibrary(String location, String lib);
}
