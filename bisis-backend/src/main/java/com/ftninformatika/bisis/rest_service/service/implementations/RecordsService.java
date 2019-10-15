package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.rest_service.exceptions.RecordNotCreatedOrUpdatedException;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.service.interfaces.RecordsServiceInterface;
import com.ftninformatika.utils.RecordUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.client.ClientSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecordsService implements RecordsServiceInterface {

    @Autowired RecordsRepository recordsRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;
    @Autowired ItemAvailabilityRepository itemAvailabilityRepository;
    @Autowired LocationRepository locationRepository;
    @Autowired LibrarianRepository librarianRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    @Autowired SublocationRepository sublocrep;
    @Autowired MongoClient mongoClient;

    private Logger log = Logger.getLogger(RecordsService.class);

    /**
     *
     * @param recordRating new rating from unique user on record
     * @param recordId record _id
     * @return avg score of rating
     */
    public long rateRecord(RecordRating recordRating, String recordId) {
        if (recordRating.getGivenRating() == null || recordRating.getLibraryMemberId() == null
        || recordRating.getUsername() == null || recordId == null)
            return -1;
        Optional<Record> rOpt = recordsRepository.findById(recordId);
        if (!rOpt.isPresent()) return -1;
        if (rOpt.get().getRecordRatings().size() > 0 && rOpt.get().getRecordRatings().stream().map(RecordRating::getLibraryMemberId)
                .anyMatch(recordRating.getLibraryMemberId()::equals)) return -1;
        Record r = rOpt.get();
        r.getRecordRatings().add(recordRating);
        if (recordsRepository.save(r) == null) return -1;
        long ratingCount = r.getRecordRatings().size();
        long sumRatings = r.getRecordRatings().stream()
                .mapToLong(RecordRating::getGivenRating).sum();
        return sumRatings / ratingCount;
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
                    LibrarianDTO modificator = null;
                    //null ce biti iz grupnog inventarisanja, zato ova provera
                    if (record.getInUseBy() != null)
                        modificator = librarianRepository.findById(record.getInUseBy()).get();
                    if (modificator != null)
                        record.getRecordModifications().add(new RecordModification(modificator.getUsername(), new Date()));
                }
                record.pack();
                Record savedRecord = recordsRepository.save(record);
                //convert record to suitable prefix-json for elasticsearch
                Map<String, List<String>> prefixes = PrefixConverter.toMap(record, null);
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

}
