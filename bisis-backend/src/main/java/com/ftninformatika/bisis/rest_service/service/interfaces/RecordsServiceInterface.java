package com.ftninformatika.bisis.rest_service.service.interfaces;

import com.ftninformatika.bisis.exception.model.RecordNotCreatedOrUpdatedException;
import com.ftninformatika.bisis.records.AvgRecordRating;
import com.ftninformatika.bisis.records.RecordRating;
import com.ftninformatika.bisis.records.Record;
import com.mongodb.MongoClientException;

public interface RecordsServiceInterface {

    /**
     *
     * @param recordRating new rating from unique user on record
     * @param recordId record _id
     * @return avg score of rating
     */
    AvgRecordRating rateRecord(RecordRating recordRating, String recordId);

    /**
     *
     * @param lib: library which sends request, for multi-tenant routing to their collection
     * @param record: record to create/update
     * @return updatedRecord
     * @throws RecordNotCreatedOrUpdatedException
     * @throws MongoClientException
     */
    Record addOrUpdateRecord(String lib, Record record) throws RecordNotCreatedOrUpdatedException, MongoClientException;

    /**
     *
     * @param _id: Record mongoId
     * @return true if deleted,else false
     * @throws IllegalArgumentException
     */
    Boolean deleteRecord(String _id) throws IllegalArgumentException;
}
