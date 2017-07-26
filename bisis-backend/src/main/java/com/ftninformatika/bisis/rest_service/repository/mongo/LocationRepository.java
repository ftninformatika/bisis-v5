package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.coders.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by dboberic on 25/07/2017.
 */

public interface LocationRepository extends MongoRepository<Location,String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Location> getLocations(String libName);
}
