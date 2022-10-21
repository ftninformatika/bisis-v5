package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Petar on 1/15/2018.
 */
public interface CounterRepository extends MongoRepository<Counter, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Counter> getCoders(String libName);
}
