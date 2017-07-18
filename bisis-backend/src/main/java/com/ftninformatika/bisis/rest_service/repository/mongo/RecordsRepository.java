package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.records.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

/**
 * Created by Petar on 6/9/2017.
 */
@RepositoryRestResource(collectionResourceRel = "records", path = "mongo_repository_records")
public interface RecordsRepository extends MongoRepository<Record, String> {

    @Query("{ 'recordID': ?0 }")
    Record getByID(@Param("id") int id);

    @Query("{ 'pubType': 1 }")
    List<Record> getFew();

    Long deleteByRecordID(@Param("id") int recId);

    List<Record> getRecordsByRecordIDIsLessThanEqual(@Param("id") int recId);



}
