package com.ftninformatika.bisis.rest_service.repository.mongo.coders;

import com.ftninformatika.bisis.coders.InternalMark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InternalMarkRepository extends MongoRepository<InternalMark, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<InternalMark> getCoders(String libName);
}
