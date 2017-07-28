package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.coders.Format;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FormatRepository extends MongoRepository<Format, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Format> getCoders(String libName);
}
