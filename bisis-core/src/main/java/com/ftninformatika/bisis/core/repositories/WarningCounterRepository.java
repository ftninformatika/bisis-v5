package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.WarningCounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningCounterRepository extends MongoRepository<WarningCounter, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<WarningCounter> getCoders(String libName);
}
