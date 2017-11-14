package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.coders.AccessionRegister;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccessionRegisterRepository extends MongoRepository<AccessionRegister, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<AccessionRegister> getCoders(String libName);
}
