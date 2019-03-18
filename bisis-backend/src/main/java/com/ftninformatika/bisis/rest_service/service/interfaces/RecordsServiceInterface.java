package com.ftninformatika.bisis.rest_service.service.interfaces;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.exceptions.RecordNotCreatedOrUpdatedException;
import com.mongodb.MongoClientException;

public interface RecordsServiceInterface {

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
