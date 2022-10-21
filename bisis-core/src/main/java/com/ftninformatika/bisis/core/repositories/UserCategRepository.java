package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.UserCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserCategRepository extends MongoRepository<UserCategory, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<UserCategory> getCoders(String libName);
}
