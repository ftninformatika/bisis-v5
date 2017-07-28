package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.coders.Sublocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SublocationRepository extends MongoRepository<Sublocation, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Sublocation> getCoders(String libName);
}
