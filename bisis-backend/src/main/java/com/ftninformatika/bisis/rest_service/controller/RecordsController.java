package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordResponseWrapper;
import com.ftninformatika.bisis.records.serializers.UnimarcSerializer;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.apache.commons.collections.IteratorUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableWebMvc
@RequestMapping("/records")
public class RecordsController {

  @Autowired RecordsRepository recordsRepository;

  @Autowired ElasticRecordsRepository elasticRecordsRepository;

  @Autowired ItemAvailabilityRepository itemAvailabilityRepository;

  @RequestMapping(method = RequestMethod.DELETE)
  public boolean deleteRecord(@RequestBody String recId){
      boolean retVal = false;
      recordsRepository.delete(recId);
      elasticRecordsRepository.delete(recId);
      return true;

  }

    @RequestMapping(value = "/getRecord")
    public Record getRecordForCtlgNum(@RequestParam (value = "ctlgno")String ctlgno){
        Record retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgno);
        if ( retVal == null )
            retVal = recordsRepository.getRecordBySveskaInvNum(ctlgno);
        return retVal;
    }

    @RequestMapping(value = "/getWrapperRecord")
    public RecordResponseWrapper getWrapperRecordForCtlgNum(@RequestParam (value = "ctlgno")String ctlgno){
        RecordResponseWrapper retVal = new RecordResponseWrapper();

        Record r = recordsRepository.getRecordByPrimerakInvNum(ctlgno);

        if ( r == null ){
            r = recordsRepository.getRecordBySveskaInvNum(ctlgno);
            retVal.setFullRecord(r);
        }

        if ( r != null ) {
            retVal.setFullRecord(r);
            retVal.setPrefixEntity(elasticRecordsRepository.findOne(r.get_id()));
            String recId = Integer.toString(r.getRecordID());
            retVal.setListOfItems(itemAvailabilityRepository.findByRecordID(recId));
        }

        return retVal;
    }


  @RequestMapping(value = "/getRecordForPrimerak")
  public Record getRecordForPrimerak(@RequestParam (value = "ctlgno")String ctlgno){
      Record retVal = null;

      retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgno);

      return retVal;
  }


  @ExceptionHandler(LockException.class)
  @RequestMapping(value = "/get_and_lock", method = RequestMethod.GET)
  public Record getAndLock(@RequestParam (value = "recId") String recId, @RequestParam (value = "librarianId") String librarianId){
      Record retVal = recordsRepository.findOne(recId);
      if(retVal.getInUseBy() != null)
          throw new LockException(recId);
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
  public boolean unlock(@RequestParam (value = "recId") String recId){
      Record r = recordsRepository.findOne(recId);
      r.setInUseBy(null);
      recordsRepository.save(r);
      return  true;
  }

    @RequestMapping(value = "/unimarc/{recordId}", method = RequestMethod.GET)
    public String getRecordUnimarc(@PathVariable String recordId) {
      String retVal = "";
        try {
            Record rec = recordsRepository.findOne(recordId);
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return UnimarcSerializer.toUNIMARC(rec.getPubType(), rec,false);
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
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

    @RequestMapping(value = "/wrapperrec/{libCode}/{recordId}", method = RequestMethod.GET)
    public RecordResponseWrapper getFullWrapperRecord(@PathVariable String recordId, @PathVariable String libCode) {
        RecordResponseWrapper retVal = new RecordResponseWrapper();
        try {
            Record rec = recordsRepository.findOne(recordId);
            ElasticPrefixEntity e = elasticRecordsRepository.findOne(recordId);
            retVal.setFullRecord(rec);
            retVal.setPrefixEntity(e);
            retVal.setListOfItems(itemAvailabilityRepository.findByRecordID(Integer.toString(rec.getRecordID())));
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return retVal;
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @RequestMapping(value = "/ep/{recordId}", method = RequestMethod.GET)
    public ElasticPrefixEntity getRecordEP(@PathVariable String recordId) {
        try {
            ElasticPrefixEntity rec = elasticRecordsRepository.findOne(recordId);
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return rec;
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @RequestMapping(value = "/wrapperrec/universal/{text}", method = RequestMethod.GET)
    public List<RecordResponseWrapper> getRecordsUniversalWrapper(@PathVariable String text){
        List<RecordResponseWrapper> retVal = new ArrayList<>();
        SimpleQueryStringBuilder query = QueryBuilders.simpleQueryStringQuery(text);
        int page=0;
        Pageable p = new PageRequest(page, 1000);
        Iterable<ElasticPrefixEntity> iRecs = elasticRecordsRepository.search(query,p);

        iRecs.forEach(
                e -> {
                    RecordResponseWrapper rw = new RecordResponseWrapper();
                    rw.setPrefixEntity(e);
                    rw.setFullRecord(recordsRepository.findOne(e.getId()));
                    rw.setListOfItems(itemAvailabilityRepository.findByRecordID(Integer.toString(rw.getFullRecord().getRecordID())));
                    retVal.add(rw);
                }
        );

        return retVal;
    }


    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<Record>> getRecords() {
        try {

            long ukupno = recordsRepository.count();
            PageRequest p = new PageRequest(1,5);
            Page<Record> recordPage = recordsRepository.findAll(p);

            List<Record> recs = recordsRepository.findAll();
            if (recs == null)
                throw new NullPointerException("Nema zapisa!");
            return new ResponseEntity<List<Record>>(recs, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @RequestMapping( value = "/ep", method = RequestMethod.GET)
    public List<ElasticPrefixEntity> getRecordsEP(){
      Iterable<ElasticPrefixEntity> s = elasticRecordsRepository.findAll();
      return IteratorUtils.toList(s.iterator());
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
            ElasticPrefixEntity ee = new ElasticPrefixEntity(savedRecord.get_id().toString(), prefixes);
            //save and index posted element via ElasticsearchRepository
            elasticRecordsRepository.save(ee);
            elasticRecordsRepository.index(ee);
            return new ResponseEntity<>(record, HttpStatus.OK);

        } catch (Exception et) {
            et.printStackTrace();
            return new ResponseEntity<>(  HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/query/full", method = RequestMethod.POST )
    public List<RecordResponseWrapper> searchFull(@RequestBody SearchModel search){
        ArrayList<RecordResponseWrapper> retVal = new ArrayList<>();

        int page=0;
        Pageable p = new PageRequest(page, 1000);
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search), p);
        ii.forEach(
                rec -> {
                    Record r = recordsRepository.findOne(rec.getId());
                    List<ItemAvailability> ia = itemAvailabilityRepository.findByRecordID(r.getRecordID()+"");
                    if (r != null)
                        retVal.add( new RecordResponseWrapper(rec, r , ia));
                }
        );
        return retVal;
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

  @RequestMapping( value = "/search_records_ep_format", method = RequestMethod.POST)
  public ResponseEntity<List<ElasticPrefixEntity>> searchRecordsElasticPrefixFormat(@RequestBody SearchModel search){
      return null;
  }

  @RequestMapping( value = "/multiple_ids", method = RequestMethod.POST )
  public ResponseEntity<List<Record>> getMultipleRecordsByIds(@RequestBody List<String> ids){
      List<Record> retVal = (List<Record>) recordsRepository.findAll(ids);
      if (retVal != null)
        return new ResponseEntity<>( retVal, HttpStatus.OK);
      else
        return new ResponseEntity<>( retVal, HttpStatus.NO_CONTENT);
  }

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
    @RequestMapping(value = "/refill_elastic", method = RequestMethod.GET)
    public ResponseEntity<Boolean> fillElastic(){

        try{
            long num = recordsRepository.count();
            int count = 0;
            Pageable p = new PageRequest(0, 1000);
            Page<Record> lr = recordsRepository.findAll(p);

            while (lr.hasNext()){
                List<ElasticPrefixEntity> ep = new ArrayList<>();
                for (Record rec: lr){
                    Map<String, String> prefixes = PrefixConverter.toMap(rec, null);
                    ElasticPrefixEntity ee = new ElasticPrefixEntity(rec.get_id().toString(), prefixes);
                    ep.add(ee);
               }
                elasticRecordsRepository.save(ep);
                count += 1000;
                System.out.println("Processed " + count + " of " + num + " records!");
                lr = recordsRepository.findAll(lr.nextPageable());
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
            }
            catch (Exception e){
                    e.printStackTrace();
                    return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
                }

            }

}
