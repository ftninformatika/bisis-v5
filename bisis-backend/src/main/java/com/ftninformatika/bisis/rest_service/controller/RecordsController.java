package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableWebMvc
@RequestMapping("/records")
public class RecordsController {

  @Autowired
  RecordsRepository recordsRepository;

  @Autowired
  ElasticRecordsRepository elasticRecordsRepository;

  @RequestMapping(value = "/get_and_lock", method = RequestMethod.GET)
  public Record getAndLock(@RequestParam (value = "recId") String recId, @RequestParam (value = "librarianId") String librarianId){
      Record retVal = recordsRepository.findOne(recId);
      retVal.setInUseBy(librarianId);
      recordsRepository.save(retVal);
      return retVal;
  }


  @RequestMapping(value = "/lock", method = RequestMethod.GET)
  public String lock(@RequestParam (value = "recId") String recId, @RequestParam (value = "librarianId") String librarianId){
      Record r = recordsRepository.findOne(recId);
      r.setInUseBy(librarianId);
      recordsRepository.save(r);
      return "Locked record with ID: " + r.get_id();
  }

  @RequestMapping(value = "/unlock", method = RequestMethod.GET)
  public String unlock(@RequestParam (value = "recId") String recId){
      Record r = recordsRepository.findOne(recId);
      r.setInUseBy(null);
      recordsRepository.save(r);
      return  "Unlocked record with ID: " + r.get_id();
  }


  @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
  public Record getRecord(@PathVariable String recordId) {
    try {
      Record rec = recordsRepository.findOne(recordId);
        if (rec == null)
        throw new RecordNotFoundException(recordId);
      return rec;
    } catch (Exception ex) {
      throw new RecordNotFoundException(recordId);
    }
  }

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<Record>> getRecords() {
        try {

            List<Record> recs = recordsRepository.findAll();
            if (recs == null)
                throw new NullPointerException("Nema zapisa!");
            return new ResponseEntity<List<Record>>(recs, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

  //dodavanje novog ili izmena postojeceg zapisa
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Record> addOrUpdate(@RequestBody Record record) {

        try {

            if(record.get_id() == null){                  //ako dodajemo novi zapis ne postoji _id, ako menjamo postoji!!!
                record.setLastModifiedDate(new Date());
                record.setCreationDate(new Date());
            }
            else
                record.setLastModifiedDate(new Date());

            //insert record in mongodb via MongoRepository
            Record savedRecord = recordsRepository.save(record);
            //convert record to suitable prefix-json for elasticsearch
            Map<String, String> prefixes = PrefixConverter.toMap(record, null);
            ElasticPrefixEntity ee = new ElasticPrefixEntity(savedRecord.get_id(), prefixes);
            //save and index posted element via ElasticsearchRepository
            elasticRecordsRepository.save(ee);
            elasticRecordsRepository.index(ee);
            return new ResponseEntity<>(record, HttpStatus.OK);

        } catch (Exception et) {
            et.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }


  }

  @RequestMapping(value = "/query", method = RequestMethod.POST )
  public ResponseEntity<List<Record>> search(@RequestBody SearchModel search){
      ArrayList<Record> retVal = new ArrayList<>();

      Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));

      System.out.println(ElasticUtility.makeQuery(search).toString());
      Iterable<String> ids = ElasticUtility.getIdsFromElasticIterable(ii);

      retVal = (ArrayList<Record>) recordsRepository.findAll(ids);

      return new ResponseEntity<List<Record>>(retVal, HttpStatus.OK);
  }

  @RequestMapping( value = "/search_ids", method = RequestMethod.POST )
  public ResponseEntity<List<String>> searchIds(@RequestBody SearchModel search){
      List<String> retVal = null;

      Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
      retVal = StreamSupport.stream(ii.spliterator(), false)
                            .map(i -> i.getId())
                            .collect(Collectors.toList());

      return  new ResponseEntity<>(retVal, HttpStatus.OK);

  }

  @RequestMapping( value = "/multiple_ids", method = RequestMethod.POST )
  public ResponseEntity<List<Record>> getMultipleRecordsByIds(@RequestBody List<String> ids){
      List<Record> retVal = (List<Record>) recordsRepository.findAll(ids);
      if (retVal != null)
        return new ResponseEntity<>( retVal, HttpStatus.OK);
      else
        return new ResponseEntity<>( retVal, HttpStatus.NO_CONTENT);
  }

  /*
  @RequestMapping( value = "/lock", method = RequestMethod.POST)
  public ResponseEntity<Boolean> lock(@RequestBody Record rec){
      Boolean retVal = null;

      recordsRepository.save(rec);
      Map<String, String> prefixes = PrefixConverter.toMap(rec, null);
      ElasticPrefixEntity ee = new ElasticPrefixEntity("" + rec.getRecordID(), prefixes); //JsonSerializer.toJson2(prefixes)
      //save and index posted element via ElasticsearchRepository
      elasticRecordsRepository.save(ee);
      elasticRecordsRepository.index(ee);

      return new ResponseEntity<Record>(retVal, HttpStatus.OK);
  }
*/

  //za testiranje!!!!
  @RequestMapping(value = "/clear_elastic", method = RequestMethod.GET)
  public ResponseEntity<Boolean> clearElastic(){
      try{
          elasticRecordsRepository.deleteAll();
          return new ResponseEntity<>(true, HttpStatus.OK);
      }
      catch (Exception e){
          e.printStackTrace();
          return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
      }

  }

    //za testiranje
    @RequestMapping(value = "/fill_elastic", method = RequestMethod.GET)
    public ResponseEntity<Boolean> fillElastic(){
        try{
            List<Record> lr = recordsRepository.findAll();
            for(Record record: lr) {
                Map<String, String> prefixes = PrefixConverter.toMap(record, null);
                ElasticPrefixEntity ee = new ElasticPrefixEntity(record.get_id(), prefixes);
                    elasticRecordsRepository.save(ee);
                    elasticRecordsRepository.index(ee);
                }
            return new ResponseEntity<>(true, HttpStatus.OK);
            }
            catch (Exception e){
                    e.printStackTrace();
                    return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
                }

            }

    public List<Record> getRecordsForMultipleIDs(ArrayList<String> ids){
        ArrayList<Record> retVal = new ArrayList<>();

        for (String id: ids){
            retVal.add(recordsRepository.findOne(id));
        }

        return retVal;
    }
}
