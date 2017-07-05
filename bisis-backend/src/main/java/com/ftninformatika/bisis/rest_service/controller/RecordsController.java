package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.JsonSerializer;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.prefixes.PrefixValue;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordsController {

  @Autowired
  RecordsRepository recordsRepository;

  @Autowired
  ElasticRecordsRepository elasticRecordsRepository;

  @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
  public Record getRecord(@PathVariable String recordId) {
    try {
      int id = Integer.parseInt(recordId);
      Record rec = recordsRepository.getByID(id);
      if (rec == null)
        throw new RecordNotFoundException(recordId);
      return rec;
    } catch (Exception ex) {
      throw new RecordNotFoundException(recordId);
    }
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Record> add(@RequestBody Record record) {

    try {
        //insert record in mongodb via MongoRepository
        recordsRepository.insert(record);

        //convert record to suitable prefix-json for elasticsearch
        List<PrefixValue> prefixes = PrefixConverter.toPrefixes(record, null);
        ElasticPrefixEntity ee = new ElasticPrefixEntity("" + record.getRecordID(), prefixes); //JsonSerializer.toJson2(prefixes);

        //save and index posted element
        elasticRecordsRepository.save(ee);
        elasticRecordsRepository.index(ee);
    } catch (Exception et){
        et.printStackTrace();
    }

    return new ResponseEntity<>(record, HttpStatus.OK);
  }

}
