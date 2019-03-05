package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.records.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Petar on 6/9/2017.
 */
@RepositoryRestResource(collectionResourceRel = "records", path = "mongo_repository_records")
public interface RecordsRepository extends MongoRepository<Record, String>, RecordsRepositoryCustom, PagingAndSortingRepository<Record, String> {

    @Query("{ 'recordID': ?0 }")
    Record getByID(@Param("id") int id);

    Long deleteByRecordID(@Param("id") int recId);

    @Query("{ 'primerci': { $elemMatch: { 'invBroj': ?0 } } }")
    Record getRecordByPrimerakInvNum(@Param("invNum") String invNum);

    @Query("{ 'primerci': { $elemMatch: { 'invBroj': ?0 } } }")
    List<Record> getRecordsByPrimerakInvNum(@Param("invNum") String invNum);

    @Query("{ 'godine.sveske': { $elemMatch: { 'invBroj': ?0 } } }")
    Record getRecordBySveskaInvNum(@Param("invNum") String invNum);

    List<Record> getRecordsByRecordIDIsLessThanEqual(@Param("id") int recId);

    @Query("{'primerci.invBroj':{$in : ?0}}")
    List<Record> getRecordsForCtlgNoList(List ctlgNos);
}
