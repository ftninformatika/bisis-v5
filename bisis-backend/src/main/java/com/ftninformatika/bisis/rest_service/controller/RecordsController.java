package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.JsonSerializer;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.prefixes.PrefixValue;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.mongodb.QueryBuilder;
import javassist.NotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.query.ElasticsearchStringQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        List<PrefixValue> prefixes = PrefixConverter.toPrefixes(record, null);
        ElasticPrefixEntity ee = new ElasticPrefixEntity("" + record.getRecordID(), prefixes); //JsonSerializer.toJson2(prefixes);

        //save and index posted element via ElasticsearchRepository
        elasticRecordsRepository.save(ee);
        elasticRecordsRepository.index(ee);
    } catch (Exception et){
        et.printStackTrace();
    }

    return new ResponseEntity<>(record, HttpStatus.OK);
  }

  @RequestMapping(value = "/query", method = RequestMethod.POST) //TODO- implementirati dolenavedeneo
  public ResponseEntity<List<Record>> search(@RequestBody String query){ //dogovoriti se oko odgovarajuceg tipa parametra za pretragu
      ArrayList<Record> retVal = new ArrayList<>();

      org.elasticsearch.index.query.QueryBuilder qb = new MatchAllQueryBuilder(); //formirati ElasticQuery od parametra(ovo je fake)
      Iterable<ElasticPrefixEntity> i = elasticRecordsRepository.search(qb); //sa elastikovog reposiztorijuma traziti sve ID-jeve elemenata koji su zadovoljili pretragu
                                                                            //struktuirati adekvatn elastik upit!!!!

      //fake recID kolekcija "vracena sa elastik repozitorijuma"
      ArrayList<String> fakeIdColletion = new ArrayList<>();
      fakeIdColletion.add("16"); fakeIdColletion.add("666"); fakeIdColletion.add("999");

      retVal = (ArrayList<Record>) getRecordsForMultipleIDs(fakeIdColletion); //sa mongo repozitorijuma preuzeti sve zapise za prosledjene id-jeve

      return new ResponseEntity<List<Record>>(retVal, HttpStatus.OK);
  }

  public List<Record> getRecordsForMultipleIDs(ArrayList<String> ids){
      ArrayList<Record> retVal = new ArrayList<>();

      for (String id: ids){
          retVal.add(recordsRepository.getByID(Integer.parseInt(id)));
      }

      return retVal;
  }

}
