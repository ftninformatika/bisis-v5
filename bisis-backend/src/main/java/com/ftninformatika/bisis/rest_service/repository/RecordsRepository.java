package com.ftninformatika.bisis.rest_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.ftninformatika.bisis.rest_service.bisis4_model.Records;

import java.util.List;

/**
 * Created by Petar on 6/9/2017.
 */
@RepositoryRestResource(collectionResourceRel = "gbns.records", path = "records")
public interface RecordsRepository extends MongoRepository<Records, String> {

    @Query("{ 'recordID': ?0 }")
    List<Records> getByID(@Param("id") int id);

}
