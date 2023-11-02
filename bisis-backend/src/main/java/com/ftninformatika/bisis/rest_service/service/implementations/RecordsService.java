package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.coders.Counter;
import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.exception.model.RecordNotCreatedOrUpdatedException;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.service.interfaces.RecordsServiceInterface;
import com.ftninformatika.utils.RecordUtils;
import com.ftninformatika.utils.RegexUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.client.ClientSession;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RecordsService implements RecordsServiceInterface {

    @Autowired
    RecordsRepository recordsRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;
    @Autowired
    ItemAvailabilityRepository itemAvailabilityRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    LibrarianRepository librarianRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    SublocationRepository sublocrep;
    @Autowired MongoClient mongoClient;
    @Autowired
    CounterRepository counterRepository;
    @Autowired
    ItemStatusRepository itemStatusRepository;

    private Logger log = Logger.getLogger(RecordsService.class);

    public AvgRecordRating rateRecord(RecordRating recordRating, String recordId) {
        if (recordRating.getGivenRating() == null || recordRating.getLibraryMemberId() == null
        || recordRating.getUsername() == null || recordId == null)
            return null;
        Optional<Record> rOpt = recordsRepository.findById(recordId);
        if (!rOpt.isPresent()) return null;
        Record r = rOpt.get();
        if (r.getRecordRatings() != null && r.getRecordRatings().size() > 0
                && r.getRecordRatings().stream().map(RecordRating::getLibraryMemberId)
                .collect(Collectors.toList()).stream().anyMatch(recordRating.getLibraryMemberId()::equals)) {
            for (RecordRating rating: r.getRecordRatings()) {
                if (rating.getLibraryMemberId().equals(recordRating.getLibraryMemberId())) {
                    r.getRecordRatings().remove(rating);
                    r.getRecordRatings().add(recordRating);
                    if (recordsRepository.save(r) == null) return null;
                    return r.getAvgRating();
                }
            }
        }
        if (r.getRecordRatings() == null) r.setRecordRatings(new ArrayList<>());
        r.getRecordRatings().add(recordRating);
        if (recordsRepository.save(r) == null) return null;
        return r.getAvgRating();
    }


    @Transactional
    public boolean mergeRecords(MergeRecordsWrapper mergeRecordsWrapper, String lib) {
        if (mergeRecordsWrapper == null || mergeRecordsWrapper.getPrimaryRecord() == null
                || mergeRecordsWrapper.getOtherRecords().size() == 0) {
            return false;
        }
        try {
            for (Record r: mergeRecordsWrapper.getOtherRecords()) {
               if (!deleteRecord(r.get_id()))
                   return false;
            }
            if (addOrUpdateRecord(lib, mergeRecordsWrapper.getPrimaryRecord()) == null)
                return false;
        } catch (RecordNotCreatedOrUpdatedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public Record addOrUpdateRecord(String lib, Record record) throws RecordNotCreatedOrUpdatedException, MongoClientException{
        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            List<String> codersNotShowable = itemStatusRepository.findAllByLibraryAndShowable(lib,false).stream().map(ItemStatus::getCoder_id).collect(Collectors.toList());
            try {
                if (record.get_id() == null) {                  //ako dodajemo novi zapis ne postoji _id, ako menjamo postoji!!!
                    record.setLastModifiedDate(new Date());
                    record.setCreationDate(new Date());

                    List<Counter> counters = counterRepository.getCoders(lib);
                    Counter c = counters.stream().filter(i -> i.getCoder_id().equals("recordid")).findFirst().orElseThrow();
                    c.setCounterValue(c.getCounterValue() + 1);
                    counterRepository.save(c);
                    record.setRecordID(c.getCounterValue());

                    c= counters.stream().filter(i -> i.getCoder_id().equals("RN")).findFirst().orElseThrow();
                    c.setCounterValue(c.getCounterValue() + 1);
                    counterRepository.save(c);
                    record.setRN(c.getCounterValue());

                    List<ItemAvailability> newItems = RecordUtils.getItemAvailabilityNewDelta(record, new Record());
                    if (newItems.size() > 0) {
                        List<Location> locs = locationRepository.getCoders(lib);
                        for (ItemAvailability i : newItems) {
                            Optional<Location> locDesc = locs.stream().filter(l -> l.getCoder_id().equals(i.getLibDepartment())).findFirst();
                            i.setLibDepartment(locDesc.get().getDescription());
                            itemAvailabilityRepository.save(i);
                        }
                    }

                }
                else {
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
                    LibrarianDB modificator = null;
                    //null ce biti iz grupnog inventarisanja, zato ova provera
                    if (record.getInUseBy() != null)
                        modificator = librarianRepository.findById(record.getInUseBy()).get();
                    if (modificator != null) {
                        record.getRecordModifications().add(new RecordModification(modificator.getUsername(), new Date()));
                    } else {
                        record.getRecordModifications().add(new RecordModification("Grupno inventarisanje", new Date()));
                    }
                }
                record.pack();
                Record savedRecord = recordsRepository.save(record);
                //convert record to suitable prefix-json for elasticsearch
                Map<String, List<String>> prefixes = PrefixConverter.toMap(record, codersNotShowable);
                ElasticPrefixEntity ee = new ElasticPrefixEntity();
                ee.setId(savedRecord.get_id());
                ee.setPrefixes(prefixes);
                elasticRecordsRepository.save(ee);
                elasticRecordsRepository.index(ee);
                session.commitTransaction();
                return savedRecord;
            }
            catch (Exception e) {
                e.printStackTrace();
                session.abortTransaction();
                throw new RecordNotCreatedOrUpdatedException(record.get_id());
            }
        } catch (MongoClientException et) {
            et.printStackTrace();
            throw new MongoClientException("Mongodb client session not made!");
        }
    }

    @Transactional
    public Boolean deleteRecord(String _id) throws IllegalArgumentException{
        Record rec = recordsRepository.findById(_id).get();
        recordsRepository.deleteById(_id);
        itemAvailabilityRepository.deleteAllByRecordID(String.valueOf(rec.getRecordID()));
        elasticsearchTemplate.delete(ElasticPrefixEntity.class, _id);
        return true;
    }

    private boolean matchesInvRange(String inv, List<String> regexStrings) {
        for (String reg: regexStrings) {
            if (Pattern.compile(reg).matcher(inv).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean validateInvNumHolesInput(String invFrom, String invTo) {
        try {
            BigInteger biFrom = new BigInteger(invFrom);
            BigInteger biTo = new BigInteger(invTo);
            if (!invFrom.substring(0,4).equals(invTo.substring(0,4)))
                return false;
            int from = Integer.parseInt(invFrom.substring(4));
            int to = Integer.parseInt(invTo.substring(4));
            if (from < 1 || from > 9999999)
                return false;
            if (to < 2 || to > 9999999)
                return false;
            if (from >= to || (to - from) > 100000)
                return false;
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public List<Integer> findInvNumHoles(String invFrom, String invTo) {
        List<Integer> retVal = new ArrayList<>();
        RegexUtils regexGenerator = new RegexUtils();
        List<String> regexes = regexGenerator.getRegex(invFrom.substring(4), invTo.substring(4));
        final List<String> allInvNo = regexes.stream().map(r -> invFrom.substring(0,4) + r).collect(Collectors.toList());

        if(!validateInvNumHolesInput(invFrom, invTo))
            return retVal;
        QueryBuilder queryBuilder = QueryBuilders.rangeQuery("prefixes.IN")
                        .gte(invFrom)
                        .lte(invTo);
        Iterable<ElasticPrefixEntity> e = elasticRecordsRepository.search(queryBuilder);
        Set<Integer> usedInvs = new HashSet<>();
        e.forEach(
                er -> {
                    er.getPrefixes().get("IN").stream().forEach(
                            i -> {
                                if (matchesInvRange(i, allInvNo))
                                    usedInvs.add(Integer.parseInt(i.substring(4)));
                            }
                    );
                }
        );

        retVal = IntStream.rangeClosed(Integer.parseInt(invFrom.substring(4)), Integer.parseInt(invTo.substring(4))).boxed().collect(Collectors.toList());
        retVal.removeAll(usedInvs);
        return retVal;
    }

}
