package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Record> add(@RequestBody Record record) {

    try {
        //insert record in mongodb via MongoRepository
        recordsRepository.insert(record);
        //convert record to suitable prefix-json for elasticsearch
        Map<String, String> prefixes = PrefixConverter.toMap(record, null);
        ElasticPrefixEntity ee = new ElasticPrefixEntity("" + record.getRecordID(), prefixes); //JsonSerializer.toJson2(prefixes)
        //save and index posted element via ElasticsearchRepository
        elasticRecordsRepository.save(ee);
        elasticRecordsRepository.index(ee);
    } catch (Exception et){
        et.printStackTrace();
    }

    return new ResponseEntity<>(record, HttpStatus.OK);
  }

  @RequestMapping(value = "/query", method = RequestMethod.POST) //TODO- implementirati dolenavedeneo
  public ResponseEntity<List<Record>> search(@RequestBody SearchModel search){ //dogovoriti se oko odgovarajuceg tipa parametra za pretragu
      ArrayList<Record> retVal = new ArrayList<>();

      Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));

      System.out.println(ElasticUtility.makeQuery(search).toString());
      retVal = (ArrayList<Record>) getRecordsForMultipleIDs(ElasticUtility.getIdsFromElasticIterable(ii)); //sa mongo repozitorijuma preuzeti sve zapise za prosledjene id-jeve

      return new ResponseEntity<List<Record>>(retVal, HttpStatus.OK);
  }

  public List<Record> getRecordsForMultipleIDs(ArrayList<String> ids){
      ArrayList<Record> retVal = new ArrayList<>();

      for (String id: ids){
          retVal.add(recordsRepository.getByID(Integer.parseInt(id)));
      }

      return retVal;
  }

      //za testiranje!!!!
      @RequestMapping(value = "/clear_elastic", method = RequestMethod.GET)
      public ResponseEntity<String> clearElastic(){
          try{
              elasticRecordsRepository.deleteAll();
              return new ResponseEntity<String>("Elastic storage cleared!", HttpStatus.OK);
          }
          catch (Exception e){
              e.printStackTrace();
              return new ResponseEntity<String>("Error!", HttpStatus.NO_CONTENT);
          }

      }
    //za testiranje
    @RequestMapping(value = "/fill_elastic", method = RequestMethod.GET)
    public ResponseEntity<String> fillElastic(){
        try{
            List<Record> lr = recordsRepository.getRecordsByRecordIDIsLessThanEqual(200);
            for(Record record: lr) {
                Map<String, String> prefixes = PrefixConverter.toMap(record, null);
                ElasticPrefixEntity ee = new ElasticPrefixEntity("" + record.getRecordID(), prefixes);
                elasticRecordsRepository.save(ee);
                elasticRecordsRepository.index(ee);
            }
            return new ResponseEntity<String>("Elastic filled with data!", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("Error!", HttpStatus.NO_CONTENT);
        }

    }

}
