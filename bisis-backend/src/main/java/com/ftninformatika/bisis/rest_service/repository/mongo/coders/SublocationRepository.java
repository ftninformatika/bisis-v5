package com.ftninformatika.bisis.rest_service.repository.mongo.coders;

import com.ftninformatika.bisis.coders.Sublocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SublocationRepository extends MongoRepository<Sublocation, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Sublocation> getCoders(String libName);


    @Query("{'coder_id': ?0, 'library': ?1}")
    Sublocation getByCoder_Id(String coder_id, String lib);
}
