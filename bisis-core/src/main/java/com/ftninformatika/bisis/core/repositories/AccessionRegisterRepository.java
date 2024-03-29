package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.AccessionRegister;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccessionRegisterRepository extends MongoRepository<AccessionRegister, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<AccessionRegister> getCoders(String libName);
}
