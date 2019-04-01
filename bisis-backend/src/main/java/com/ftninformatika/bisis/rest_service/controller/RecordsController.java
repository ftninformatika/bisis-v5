package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.records.serializers.UnimarcSerializer;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.exceptions.LockException;
import com.ftninformatika.bisis.rest_service.exceptions.RecordNotCreatedOrUpdatedException;
import com.ftninformatika.bisis.rest_service.exceptions.RecordNotFoundException;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryConfigService;
import com.ftninformatika.bisis.rest_service.service.implementations.RecordsService;
import com.ftninformatika.bisis.search.*;
import com.ftninformatika.util.elastic.ElasticUtility;
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
import sun.util.resources.cldr.br.CurrencyNames_br;

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
    @Autowired RecordsService recordsService;
    @Autowired LibraryConfigService lcService;
    @Autowired LibraryPrefixProvider libraryPrefixProvider;

    private Logger log = Logger.getLogger(MemberController.class);

    @PostMapping("/mergeRecords")
    public ResponseEntity<Boolean> mergeRecords(@RequestHeader("Library") String lib,
                                                @RequestBody MergeRecordsWrapper mergeRecordsWrapper) {
        boolean retVal = recordsService.mergeRecords(mergeRecordsWrapper, lib);
        if(retVal)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/findInvHoles")
    public List<Integer> findInvHoles(@RequestParam(value = "invFrom")String invFrom, @RequestParam(value = "invTo")String invTo) {
        return recordsRepository.findInvNumHoles(invFrom, invTo);
    }

    @GetMapping("/delete/{mongoID}")
    public ResponseEntity<Boolean> deleteRecord(@PathVariable("mongoID") String mongoID) {
        Boolean retVal = recordsService.deleteRecord(mongoID);
        if (retVal) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
    }


    @GetMapping("/getRecord")
    public Record getRecordForCtlgNum(@RequestParam(value = "ctlgno") String ctlgno) {
        Record retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgno);
        if (retVal == null)
            retVal = recordsRepository.getRecordBySveskaInvNum(ctlgno);
        return retVal;
    }

    @GetMapping("/getWrapperRecord")
    public RecordResponseWrapper getWrapperRecordForCtlgNum(@RequestParam(value = "ctlgno") String ctlgno) {
        RecordResponseWrapper retVal = new RecordResponseWrapper();

        Record r = recordsRepository.getRecordByPrimerakInvNum(ctlgno);

        if (r == null) {
            r = recordsRepository.getRecordBySveskaInvNum(ctlgno);
            retVal.setFullRecord(r);
        }

        if (r != null) {
            retVal.setFullRecord(r);
            String recId = Integer.toString(r.getRecordID());
            retVal.setListOfItems(itemAvailabilityRepository.findByRecordID(recId));
        }

        return retVal;
    }


    @GetMapping("/getRecordForPrimerak")
    public Record getRecordForPrimerak(@RequestParam(value = "ctlgno") String ctlgno) {
        Record retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgno);
        return retVal;
    }


    @GetMapping("/get_and_lock")
    public Record getAndLock(@RequestParam(value = "recId") String recId, @RequestParam(value = "librarianId") String librarianId) {
        Record retVal = recordsRepository.findById(recId).get();
        if (retVal.getInUseBy() != null)
            throw new LockException(recId);
        retVal.setInUseBy(librarianId);
        recordsRepository.save(retVal);
        return retVal;
    }

    @GetMapping("/get_by_rn")
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


    @GetMapping(value = "/lock")
    public String lock(@RequestParam(value = "recId") String recId, @RequestParam(value = "librarianId") String librarianId) {
        Record r = recordsRepository.findById(recId).get();
        r.setInUseBy(librarianId);
        recordsRepository.save(r);
        return "Locked record with ID: " + r.get_id();
    }

    @GetMapping("/unlock")
    public boolean unlock(@RequestParam(value = "recId") String recId) {
        Record r = recordsRepository.findById(recId).get();
        r.setInUseBy(null);
        recordsRepository.save(r);
        return true;
    }

    @GetMapping("/unlock_by_rn")
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

    @GetMapping("/unimarc/{recordId}")
    public String getRecordUnimarc(@PathVariable String recordId) {
        try {
            Record rec = recordsRepository.findById(recordId).get();
            if (rec == null)
                throw new RecordNotFoundException(recordId);
            return UnimarcSerializer.toUNIMARC(rec.getPubType(), rec, false);
        } catch (Exception ex) {
            throw new RecordNotFoundException(recordId);
        }
    }

    @GetMapping("/{recordId}")
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

    @PostMapping("/get_foreign_record")
    public ResponseEntity<Record> getForeignRecord(@RequestBody BriefInfoModel bim) {
        if (bim == null || bim.getBriefInfo() == null
        || bim.getBriefInfo().get_id() == null || "".equals(bim.getBriefInfo().get_id())
        || bim.getLibPrefix() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        libraryPrefixProvider.setPrefix(bim.getLibPrefix());
        Record retVal = recordsRepository.findById(bim.getBriefInfo().get_id()).get();
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping("/wrapperrec/{recordId}")
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

    @GetMapping("/opac_wrapperrec/{recordId}")
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

    @GetMapping("/ep/{recordId}")
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

    @PostMapping("/wrapperrec/universal")
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

    @PostMapping("/wrapperrec/opac_universal")
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


    @GetMapping("/wrapperrec/universal/page/{text}")
    public Page<ElasticPrefixEntity> getRecordsUniversalPages(@PathVariable String text) {
        List<RecordResponseWrapper> retVal = new ArrayList<>();
        SimpleQueryStringBuilder query = QueryBuilders.simpleQueryStringQuery(text);
        int page = 0;
        Pageable p = new PageRequest(page, 100);
        Page<ElasticPrefixEntity> iRecs = elasticRecordsRepository.search(query, p);

        return iRecs;
    }

    @GetMapping("/checkInv/{invNum}")
    public Boolean checkInv(@PathVariable("invNum") String invNum) {
        Iterable<ElasticPrefixEntity> e = elasticRecordsRepository.search(ElasticUtility.idExistsQuery(invNum));
        return IteratorUtils.toList(e.iterator()).size() != 0;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Record> addOrUpdate(@RequestHeader("Library") String lib, @RequestBody Record record) {
        Record retVal = null;
        try {
            retVal = recordsService.addOrUpdateRecord(lib, record);
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (RecordNotCreatedOrUpdatedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(retVal, HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(retVal, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/query")
    public ResponseEntity<List<Record>> search(@RequestBody SearchModel search, @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        ArrayList<Record> retVal = new ArrayList<>();
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
        Iterable<String> ids = ElasticUtility.getIdsFromElasticIterable(ii);
        retVal = (ArrayList<Record>) recordsRepository.findAllById(ids);

        return new ResponseEntity<List<Record>>(retVal, HttpStatus.OK);
    }

    @PostMapping("/query/full")
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

    @PostMapping(value = "/query/opac_full")
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

    @PostMapping("/search_ids/multiple_libs")
    public ResponseEntity<Vector<BriefInfoModel>> searchIdsMutlipleLibs(@RequestBody OtherLibsSearch otherLibsSearch) {
        Vector<BriefInfoModel> retVal = null;
        Map<String, LibraryConfiguration> lcMap = lcService.getAllLibraryConfigMap();

        if (otherLibsSearch.getLibraries().size() == 0 || otherLibsSearch.getSearchModel() == null
            || !lcMap.keySet().containsAll(otherLibsSearch.getLibraries()))
            return new ResponseEntity<>(retVal, HttpStatus.NOT_ACCEPTABLE);

        retVal = new Vector<>();
        for (String lib: otherLibsSearch.getLibraries()) {
            // For routing
            libraryPrefixProvider.setPrefix(lib);
            Result res = searchIdsResult(otherLibsSearch.getSearchModel()).getBody();
            if (res.getRecords() == null || res.getRecords().length == 0)
                continue;

            // Limit of records per library TODO - hardcoded, move to some place nicer
            if (res.getRecords().length > 2000)
                return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);

            for (String _id: res.getRecords()) {
                BriefInfoModel bi = new BriefInfoModel();
                bi.setLibPrefix(lib);
                bi.setLibFullName(lcMap.get(lib).getLibraryFullName());
                RecordBriefs rb = new RecordBriefs();
                RecordPreview rp = new RecordPreview();
                rp.init(recordsRepository.findById(_id).get());
                rb.set_id(_id);
                rb.setAutor(rp.getAuthor());
                rb.setPublicYear(rp.getPublishingYear());
                rb.setPublisher(rp.getPublisher());
                rb.setTitle(rp.getTitle());
                bi.setBriefInfo(rb);
                retVal.add(bi);
            }
        }

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping("/get_mixed_library_records")
    public ResponseEntity<Vector<Record>> getMixedLibraryRecords(@RequestBody Vector<BriefInfoModel> currentBriefs) {
        Vector<Record> retVal = new Vector<>();
        if (currentBriefs == null || currentBriefs.size() == 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        for (BriefInfoModel bim: currentBriefs) {
            libraryPrefixProvider.setPrefix(bim.getLibPrefix());
            if (bim.getBriefInfo() == null || bim.getBriefInfo().get_id() == null
            || "".equals(bim.getBriefInfo().get_id()) || !recordsRepository.findById(bim.getBriefInfo().get_id()).isPresent()) {
                log.warn("Not valid record brief: " + bim.toString());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Record r = recordsRepository.findById(bim.getBriefInfo().get_id()).get();
            retVal.add(r);
        }
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }


    @PostMapping("/search_ids")
    public ResponseEntity<List<String>> searchIds(@RequestBody SearchModel search) {
        List<String> retVal = null;

        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
        retVal = StreamSupport.stream(ii.spliterator(), false)
                .map(i -> i.getId())
                .collect(Collectors.toList());

        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }

    @PostMapping(value = "/search_ids_result")
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

    @PostMapping("/search_ids_circ")
    public ResponseEntity<List<String>> searchIdsCirc(@RequestBody SearchModelCirc search) {
        List<String> retVal = null;

        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(search));
        retVal = StreamSupport.stream(ii.spliterator(), false)
                .map(i -> i.getId())
                .collect(Collectors.toList());

        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }

    @PostMapping("/lockByRedactor/{recId}")
    public Boolean lockByRedactor(@PathVariable("recId") String recId) {
        Record r = recordsRepository.findById(recId).get();
        r.setLockedByRedactor(true);
        recordsRepository.save(r);
        return r.isLockedByRedactor();
    }

    @PostMapping("/unlockByRedactor/{recId}")
    public Boolean unlockByRedactor(@PathVariable("recId") String recId) {
        Record r = recordsRepository.findById(recId).get();
        r.setLockedByRedactor(false);
        recordsRepository.save(r);
        return r.isLockedByRedactor();
    }

    @PostMapping("/multiple_ids")
    public ResponseEntity<List<Record>> getMultipleRecordsByIds(@RequestBody List<String> ids) {
        List<Record> retVal = (List<Record>) recordsRepository.findAllById(ids);
        retVal.sort(Comparator.comparingDouble(r -> ids.indexOf(r.get_id())));
        if (retVal != null)
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        else
            return new ResponseEntity<>(retVal, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/multiple_ids_wrapper")
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
