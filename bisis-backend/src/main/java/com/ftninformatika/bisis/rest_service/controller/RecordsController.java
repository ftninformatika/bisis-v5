package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.ExceptionListener;

@RestController
@RequestMapping("/records")
public class RecordsController {

  @Autowired
  RecordsRepository recordsRepository;

  @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
  public Record getRecord(@PathVariable String recordId) {
    try {
      int id = Integer.parseInt(recordId);
      Record rec = recordsRepository.getByID(id);
      return rec;
    } catch (Exception ex) {
      throw new RecordNotFoundException(recordId);
    }
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Record> add(@RequestBody Record record) {
    //recordsRepository.add(record);
    return new ResponseEntity<>(record, HttpStatus.OK);
  }

}
