package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.records.serializers.UnimarcSerializer;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.search.Result;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.SearchModelCirc;
import com.ftninformatika.bisis.search.UniversalSearchModel;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.RecordUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.util.CloseableIterator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/records")
public class RecordsController {

    @Autowired RecordsRepository recordsRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;
    @Autowired ItemAvailabilityRepository itemAvailabilityRepository;
    @Autowired LocationRepository locationRepository;
    @Autowired LibrarianRepository librarianRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    @Autowired SublocationRepository sublocrep;
    @Autowired MongoClient mongoClient;

    private Logger log = Logger.getLogger(MemberController.class);

    @RequestMapping(value = "/findInvHoles")
    public List<Integer> findInvHoles(@RequestParam(value = "invFrom")String invFrom, @RequestParam(value = "invTo")String invTo) {
        List<Integer> retVal = new ArrayList<>();

        retVal = recordsRepository.findInvNumHoles(invFrom, invTo);

        return retVal;
    }

    @RequestMapping(value = "/delete/{mongoID}")
    @Transactional
    public Boolean deleteRecord(@PathVariable("mongoID") String mongoID) {
        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            try {
                Record rec = recordsRepository.findById(mongoID).get();
                recordsRepository.deleteById(mongoID);
                itemAvailabilityRepository.deleteAllByRecordID(String.valueOf(rec.getRecordID()));
                session.commitTransaction();
                elasticsearchTemplate.delete(ElasticPrefixEntity.class, mongoID);
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
                session.abortTransaction();
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @RequestMapping(value = "/getRecord")
    public Record getRecordForCtlgNum(@RequestParam(value = "ctlgno") String ctlgno) {
        Record retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgno);
        if (retVal == null)
            retVal = recordsRepository.getRecordBySveskaInvNum(ctlgno);
        return retVal;
    }

    @RequestMapping(value = "/getWrapperRecord")
    public RecordResponseWrapper getWrapperRecordForCtlgNum(@RequestParam(value = "ctlgno") String ctlgno) {
        RecordResponseWrapper retVal = new RecordResponseWrapper();

        Record r = recordsRepository.getRecordByPrimerakInvNum(ctlgno);

        if (r == null) {
            r = recordsRepository.getRecordBySveskaInvNum(ctlgno);
            retVal.setFullRecord(r);
        }

        if (r != null) {
            retVal.setFullRecord(r);
            //retVal.setPrefixEntity(elasticRecordsRepository.findOne(r.get_id()));
            String recId = Integer.toString(r.getRecordID());
            retVal.setListOfItems(itemAvailabilityRepository.findByRecordID(recId));
        }

        return retVal;
    }


    @RequestMapping(value = "/getRecordForPrimerak")
    public Record getRecordForPrimerak(@RequestParam(value = "ctlgno") String ctlgno) {
        Record retVal = null;

        retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgno);

        return retVal;
    }


    @RequestMapping(value = "/get_and_lock", method = RequestMethod.GET)
    public Record getAndLock(@RequestParam(value = "recId") String recId, @RequestParam(value = "librarianId") String librarianId) {
        Record retVal = recordsRepository.findById(recId).get();
        if (retVal.getInUseBy() != null)
            throw new LockException(recId);
        retVal.setInUseBy(librarianId);
        recordsRepository.save(retVal);
        return retVal;
    }

    @RequestMapping(value = "/get_by_rn", method = RequestMethod.GET)
    public Record getRecordByRN(@RequestParam("rn") String rn){
        Iterable<ElasticPrefixEntity> e = elasticRecordsRepository.search(QueryBuilders.matchPhrasePrefixQuery("prefixes.RN", rn));
        List<String> ids = new ArrayList<>();
        e.forEach(
                er -> {
                    ids.add(er.getId());
                }
        );
        if (ids.size() == 1)
            return recordsRepository.findById(ids.get(0)).get();
        else
            return null;
    }


    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public String lock(@RequestParam(value = "recId") String recId, @RequestParam(value = "librarianId") String librarianId) {
        Record r = recordsRepository.findById(recId).get();
        r.setInUseBy(librarianId);
        recordsRepository.save(r);
        return "Locked record with ID: " + r.get_id();
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.GET)
    public boolean unlock(@RequestParam(value = "recId") String recId) {
        Record r = recordsRepository.findById(recId).get();
        r.setInUseBy(null);
        recordsRepository.save(r);
        return true;
    }

    @RequestMapping(value = "/unlock_by_rn", method = RequestMethod.GET)
    public boolean unlockByRN(@RequestParam(value = "rn") String rn) {
        Iterable<ElasticPrefixEntity> e = elasticRecordsRepository.search(QueryBuilders.matchPhrasePrefixQuery("prefixes.RN", rn));
        List<String> ids = new ArrayList<>();
        e.forEach(
                er -> {
                    ids.add(er.getId());
                }
        );
        if (ids.size() == 1) {
            Record r =  recordsRepository.findById(ids.get(0)).get();
            r.setInUseBy(null);
            recordsRepository.save(r);
            return true;
        }
        else
            return false;
    }

    @RequestMapping(value = "/unimarc/{recordId}", method = RequestMethod.GET)
    public String getRecordUnimarc(@PathVariable String recordId) {
        String retVal = "";
        try {
            Record rec = recordsRepository.findById(recordId).get();
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return UnimarcSerializer.toUNIMARC(rec.getPubType(), rec, false);
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
    public Record getRecord(@PathVariable String recordId) {
        try {
            Record rec = recordsRepository.findById(recordId).get();
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return rec;
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @RequestMapping(value = "/wrapperrec/{recordId}", method = RequestMethod.GET)
    public RecordResponseWrapper getFullWrapperRecord(@RequestHeader("Library") String lib, @PathVariable String recordId) {
        RecordResponseWrapper retVal = new RecordResponseWrapper();
        try {
            Record rec = recordsRepository.findById(recordId).get();
            RecordPreview pr = new RecordPreview();
            pr.init(rec);
            ElasticPrefixEntity e = elasticRecordsRepository.findById(recordId).get();
            retVal.setFullRecord(rec);
            retVal.setRecordPreview(pr);
            retVal.setListOfItems(itemAvailabilityRepository.findByRecordID(Integer.toString(rec.getRecordID())));
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return retVal;
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @RequestMapping(value = "/opac_wrapperrec/{recordId}", method = RequestMethod.GET)
    public RecordOpacResponseWrapper getFullOpacWrapperRecord(@RequestHeader("Library") String lib, @PathVariable String recordId) {
        RecordOpacResponseWrapper retVal = new RecordOpacResponseWrapper();
        try {
            Record rec = recordsRepository.findById(recordId).get();
            RecordPreview pr = new RecordPreview();
            pr.init(rec);
            ElasticPrefixEntity e = elasticRecordsRepository.findById(recordId).get();
            retVal.setFullRecord(rec);
            retVal.setRecordPreview(pr);
            List<ItemAvailability> itemAvailabilities = itemAvailabilityRepository.findByRecordID(Integer.toString(rec.getRecordID()));
            Map<String, String> sublocationMap = sublocrep.getCoders(lib).stream().collect(Collectors.toMap(sl -> sl.getCoder_id(), sl -> sl.getDescription()));
            List<PrimerakPreview> primerakPreviews = new ArrayList<>();
            for (ItemAvailability ia: itemAvailabilities) {
                String sublocation = rec.getPrimerak(ia.getCtlgNo()).getSigPodlokacija();
                PrimerakPreview p = lib.equals("bgb") ? new PrimerakPreview(ia, sublocationMap.get(sublocation)) : new PrimerakPreview(ia, ia.getLibDepartment());
                primerakPreviews.add(p);
            }
            retVal.setListOfItems(primerakPreviews);
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
            ElasticPrefixEntity rec = elasticRecordsRepository.findById(recordId).get();
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return rec;
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @RequestMapping(value = "/wrapperrec/universal", method = RequestMethod.POST)
    public Page<RecordResponseWrapper> getRecordsUniversalWrapper(@RequestBody UniversalSearchModel universalSearchModel, @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {


        List<RecordResponseWrapper> retVal = new ArrayList<>();

        BoolQueryBuilder query = ElasticUtility.searchUniversalQuery(universalSearchModel);
        int page = 0;
        int pSize = 20;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;


        Pageable p = new PageRequest(page, pSize);
        Iterable<ElasticPrefixEntity> iRecs = elasticRecordsRepository.search(query, new PageRequest(page, pSize/*, new Sort(Sort.Direction.ASC, "prefixes.AU")*/));

        iRecs.forEach(
                e -> {
                    RecordResponseWrapper rw = new RecordResponseWrapper();
                    Record r = recordsRepository.findById(e.getId()).get();
                    RecordPreview pr = new RecordPreview();
                    pr.init(r);
                    rw.setFullRecord(r);
                    rw.setListOfItems(itemAvailabilityRepository.findByRecordID(Integer.toString(rw.getFullRecord().getRecordID())));
                    rw.setRecordPreview(pr);
                    retVal.add(rw);
                }
        );

        Page<RecordResponseWrapper> rr = new PageImpl<RecordResponseWrapper>(retVal, p, ((Page<ElasticPrefixEntity>) iRecs).getTotalElements());
        return rr;
    }

    @RequestMapping(value = "/wrapperrec/opac_universal", method = RequestMethod.POST)
    public Page<RecordOpacResponseWrapper> getRecordsOpacUniversalWrapper(@RequestHeader("Library") String lib,@RequestBody UniversalSearchModel universalSearchModel, @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {

        List<RecordOpacResponseWrapper> retVal = new ArrayList<>();

        BoolQueryBuilder query = ElasticUtility.searchUniversalQuery(universalSearchModel);
        int page = 0;
        int pSize = 20;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;


        Pageable p = new PageRequest(page, pSize);
        Iterable<ElasticPrefixEntity> iRecs = elasticRecordsRepository.search(query, new PageRequest(page, pSize/*, new Sort(Sort.Direction.ASC, "prefixes.AU")*/));

        iRecs.forEach(
                e -> {
                    RecordOpacResponseWrapper rw = new RecordOpacResponseWrapper();
                    Record r = recordsRepository.findById(e.getId()).get();
                    RecordPreview pr = new RecordPreview();
                    pr.init(r);
                    List<ItemAvailability> itemAvailabilities = itemAvailabilityRepository.findByRecordID(Integer.toString(r.getRecordID()));
                    Map<String, String> sublocationMap = sublocrep.getCoders(lib).stream().collect(Collectors.toMap(sl -> sl.getCoder_id(), sl -> sl.getDescription()));
                    List<PrimerakPreview> primerakPreviews = new ArrayList<>();
                    for (ItemAvailability ia: itemAvailabilities) {
                        String sublocation = r.getPrimerak(ia.getCtlgNo()).getSigPodlokacija();
                        PrimerakPreview pp = lib.equals("bgb") ? new PrimerakPreview(ia, sublocationMap.get(sublocation)) : new PrimerakPreview(ia, ia.getLibDepartment());
                        primerakPreviews.add(pp);
                    }
                    rw.setFullRecord(r);
                    rw.setListOfItems(primerakPreviews);
                    rw.setRecordPreview(pr);
                    retVal.add(rw);
                }
        );

        Page<RecordOpacResponseWrapper> rr = new PageImpl<RecordOpacResponseWrapper>(retVal, p, ((Page<ElasticPrefixEntity>) iRecs).getTotalElements());
        return rr;
    }


    @RequestMapping(value = "/wrapperrec/universal/page/{text}", method = RequestMethod.GET)
    public Page<ElasticPrefixEntity> getRecordsUniversalPages(@PathVariable String text) {
        List<RecordResponseWrapper> retVal = new ArrayList<>();
        SimpleQueryStringBuilder query = QueryBuilders.simpleQueryStringQuery(text);
        int page = 0;
        Pageable p = new PageRequest(page, 100);
        Page<ElasticPrefixEntity> iRecs = elasticRecordsRepository.search(query, p);


        return iRecs;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Record> getRecords() {


        long ukupno = recordsRepository.count();
        PageRequest p = new PageRequest(1, 5);
        Page<Record> recordPage = recordsRepository.findAll(p);

        if (recordPage == null)
            throw new NullPointerException("Nema zapisa!");
        return recordPage;

    }

    @RequestMapping(value = "/ep", method = RequestMethod.GET)
    public List<ElasticPrefixEntity> getRecordsEP() {
        Iterable<ElasticPrefixEntity> s = elasticRecordsRepository.findAll();
        return IteratorUtils.toList(s.iterator());
    }

    @RequestMapping(value = "/checkInv/{invNum}")
    public Boolean checkInv(@PathVariable("invNum") String invNum) {
        Iterable<ElasticPrefixEntity> e = elasticRecordsRepository.search(ElasticUtility.idExistsQuery(invNum));
        return IteratorUtils.toList(e.iterator()).size() != 0;
    }

    //dodavanje novog ili izmena postojeceg zapisa
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Record> addOrUpdate(@RequestHeader("Library") String lib, @RequestBody Record record) {

        try (ClientSession session = mongoClient.startSession()) {

            session.startTransaction();
            try {
                if (record.get_id() == null) {                  //ako dodajemo novi zapis ne postoji _id, ako menjamo postoji!!!
                    record.setLastModifiedDate(new Date());
                    record.setCreationDate(new Date());

                    List<ItemAvailability> newItems = RecordUtils.getItemAvailabilityNewDelta(record, new Record());
                    if (newItems.size() > 0) {
                        List<Location> locs = locationRepository.getCoders(lib);
                        for (ItemAvailability i : newItems) {
                            Optional<Location> locDesc = locs.stream().filter(l -> l.getCoder_id().equals(i.getLibDepartment())).findFirst();
                            i.setLibDepartment(locDesc.get().getDescription());
                            itemAvailabilityRepository.save(i);
                        }
                    }

                } else {// ovo znaci da je update
                    record.setLastModifiedDate(new Date());
                    Record storedRec = recordsRepository.findById(record.get_id()).get();
                    List<ItemAvailability> newItems = RecordUtils.getItemAvailabilityNewDelta(record, storedRec); //novi primerci - pretabani u ItemAvailability
                    if (newItems.size() > 0) {
                        List<Location> locs = locationRepository.getCoders(lib);
                        for (ItemAvailability i : newItems) {
                            Optional<Location> locDesc = locs.stream().filter(l -> l.getCoder_id().equals(i.getLibDepartment())).findFirst();
                            i.setLibDepartment(locDesc.get().getDescription());
                            itemAvailabilityRepository.save(i);
                        }
                    }
                    List<String> deletedInvs = RecordUtils.getDeletedInvNumsDelta(record, storedRec); //lista inv brojeva obrisanih primeraka
                    if (deletedInvs.size() > 0)
                        itemAvailabilityRepository.deleteByCtlgNoIn(deletedInvs);

                    //posto je obradjivan, mora da je inUseBy popunjen mongoId- jem bibliotekara!
                    LibrarianDTO modificator = null;
                    //null ce biti iz grupnog inventarisanja, zato ova provera
                    if (record.getInUseBy() != null)
                        modificator = librarianRepository.findById(record.getInUseBy()).get();
                    if (modificator != null)
                        record.getRecordModifications().add(new RecordModification(modificator.getUsername(), new Date()));

                }


                record.pack();
                //insert record in mongodb via MongoRepository
                Record savedRecord = recordsRepository.save(record);
                session.commitTransaction();
                //convert record to suitable prefix-json for elasticsearch
                Map<String, List<String>> prefixes = PrefixConverter.toMap(record, null);
                ElasticPrefixEntity ee = new ElasticPrefixEntity();
                ee.setId(savedRecord.get_id());
                ee.setPrefixes(prefixes);
                elasticRecordsRepository.save(ee);
                elasticRecordsRepository.index(ee);
                return new ResponseEntity<>(savedRecord, HttpStatus.OK);
            }
            catch (Exception e) {
                e.printStackTrace();
                session.abortTransaction();
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        } catch (Exception et) {
            et.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity<List<Record>> search(@RequestBody SearchModel search, @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        ArrayList<Record> retVal = new ArrayList<>();
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
        Iterable<String> ids = ElasticUtility.getIdsFromElasticIterable(ii);
        retVal = (ArrayList<Record>) recordsRepository.findAllById(ids);

        return new ResponseEntity<List<Record>>(retVal, HttpStatus.OK);
    }

    @RequestMapping(value = "/query/full", method = RequestMethod.POST)
    public Page<RecordResponseWrapper> searchFull(@RequestBody SearchModel search, @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        ArrayList<RecordResponseWrapper> retVal = new ArrayList<>();

        int page = 0;
        int pSize = 20;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;

        Pageable p = new PageRequest(page, pSize);
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search), p);
        ii.forEach(
                rec -> {
                    Record r = recordsRepository.findById(rec.getId()).get();
                    List<ItemAvailability> ia = itemAvailabilityRepository.findByRecordID(r.getRecordID() + "");
                    RecordPreview pr = new RecordPreview();
                    pr.init(r);
                    if (r != null)
                        retVal.add(new RecordResponseWrapper(r, pr, ia));
                }
        );
        return new PageImpl<RecordResponseWrapper>(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }

    @RequestMapping(value = "/query/opac_full", method = RequestMethod.POST)
    public Page<RecordOpacResponseWrapper> searchFull(@RequestHeader("Library") String lib, @RequestBody SearchModel search, @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        ArrayList<RecordOpacResponseWrapper> retVal = new ArrayList<>();

        int page = 0;
        int pSize = 20;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;

        Pageable p = new PageRequest(page, pSize);
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search), p);
        ii.forEach(
                rec -> {
                    Record r = recordsRepository.findById(rec.getId()).get();
                    List<ItemAvailability> ias = itemAvailabilityRepository.findByRecordID(r.getRecordID() + "");
                    RecordPreview pr = new RecordPreview();
                    pr.init(r);
                    Map<String, String> sublocationMap = sublocrep.getCoders(lib).stream().collect(Collectors.toMap(sl -> sl.getCoder_id(), sl -> sl.getDescription()));
                    List<PrimerakPreview> primerakPreviews = new ArrayList<>();
                    for (ItemAvailability ia: ias) {
                        String sublocation = r.getPrimerak(ia.getCtlgNo()).getSigPodlokacija();
                        PrimerakPreview pp = lib.equals("bgb") ? new PrimerakPreview(ia, sublocationMap.get(sublocation)) : new PrimerakPreview(ia, ia.getLibDepartment());
                        primerakPreviews.add(pp);
                    }
                    if (r != null)
                        retVal.add(new RecordOpacResponseWrapper(r, pr, primerakPreviews));
                }
        );
        return new PageImpl<RecordOpacResponseWrapper>(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }

    @RequestMapping(value = "/search_ids", method = RequestMethod.POST)
    public ResponseEntity<List<String>> searchIds(@RequestBody SearchModel search) {
        List<String> retVal = null;

        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
        retVal = StreamSupport.stream(ii.spliterator(), false)
                .map(i -> i.getId())
                .collect(Collectors.toList());

        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }

    @RequestMapping(value = "/search_ids_result", method = RequestMethod.POST)
    public ResponseEntity<Result> searchIdsResult(@RequestBody SearchModel search) {
        Result retVal = new Result();
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> ctlgnos = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 5000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(ElasticUtility.makeQuery(search))
                .withPageable(pageable)
                .build();

        CloseableIterator<ElasticPrefixEntity> streamEs = elasticsearchTemplate.stream(searchQuery, ElasticPrefixEntity.class);
        while (streamEs.hasNext()) {
            ElasticPrefixEntity e = streamEs.next();
            ids.add(e.getId());
            if (e.getPrefixes().get("IN") != null && e.getPrefixes().size() > 0)
                ctlgnos.addAll(e.getPrefixes().get("IN"));
        }

        retVal.setRecords(ids.toArray(new String[ids.size()]));
        retVal.setInvs(ctlgnos);
        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }

    @RequestMapping(value = "/search_ids_circ", method = RequestMethod.POST)
    public ResponseEntity<List<String>> searchIdsCirc(@RequestBody SearchModelCirc search) {
        List<String> retVal = null;

        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
        retVal = StreamSupport.stream(ii.spliterator(), false)
                .map(i -> i.getId())
                .collect(Collectors.toList());

        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }

    @RequestMapping(value = "/search_records_ep_format", method = RequestMethod.POST)
    public ResponseEntity<List<ElasticPrefixEntity>> searchRecordsElasticPrefixFormat(@RequestBody SearchModel search) {
        return null;
    }

    @RequestMapping(value = "/lockByRedactor/{recId}", method = RequestMethod.GET)
    public Boolean lockByRedactor(@PathVariable("recId") String recId) {
        Record r = recordsRepository.findById(recId).get();
        r.setLockedByRedactor(true);
        recordsRepository.save(r);
        return r.isLockedByRedactor();
    }

    @RequestMapping(value = "/unlockByRedactor/{recId}", method = RequestMethod.GET)
    public Boolean unlockByRedactor(@PathVariable("recId") String recId) {
        Record r = recordsRepository.findById(recId).get();
        r.setLockedByRedactor(false);
        recordsRepository.save(r);
        return r.isLockedByRedactor();
    }

    @RequestMapping(value = "/multiple_ids", method = RequestMethod.POST)
    public ResponseEntity<List<Record>> getMultipleRecordsByIds(@RequestBody List<String> ids) {
        List<Record> retVal = (List<Record>) recordsRepository.findAllById(ids);
        retVal.sort(Comparator.comparingDouble(r -> ids.indexOf(r.get_id())));
        if (retVal != null)
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        else
            return new ResponseEntity<>(retVal, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/multiple_ids_wrapper", method = RequestMethod.POST)
    public List<RecordResponseWrapper> getRecordsAllDataByIds(@RequestBody List<String> idList) {
        List<RecordResponseWrapper> retVal = new ArrayList<>();
        Iterable<Record> recs = recordsRepository.findAllById(idList);
        recs.forEach(
                record -> {
                    RecordResponseWrapper wrapper = new RecordResponseWrapper();
                    wrapper.setFullRecord(record);
                    List<ItemAvailability> items = itemAvailabilityRepository.findByRecordID("" + record.getRecordID());
                    wrapper.setListOfItems(items);
                    retVal.add(wrapper);
                }
        );
        return retVal;
    }

}
