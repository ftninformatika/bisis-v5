package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.coders.InternalMark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InternalMarkRepository extends MongoRepository<InternalMark, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<InternalMark> getCoders(String libName);
}
