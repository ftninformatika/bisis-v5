package com.ftninformatika.bisis.rest_service.service.interfaces;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.exceptions.RecordNotCreatedOrUpdatedException;
import com.mongodb.MongoClientException;

public interface RecordsServiceIterface {

    public Record addOrUpdateRecord(String lib, Record record) throws RecordNotCreatedOrUpdatedException, MongoClientException;
}
