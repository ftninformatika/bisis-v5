package com.ftninformatika.bisis.rest_service.repository.mongo.coders;

import com.ftninformatika.bisis.records.ItemAvailability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Created by Petar on 10/19/2017.
 */
public interface ItemAvailabilityRepository extends MongoRepository<ItemAvailability, String>, PagingAndSortingRepository<ItemAvailability,String>{

    public List<ItemAvailability> findByRecordID(String recId);

    public ItemAvailability getByCtlgNo(String ctlno);

    public List<ItemAvailability> findAllByCtlgNo (String ctlgNo);

    public void deleteByCtlgNoIn (List<String> ctlgnos);

    public void deleteByCtlgNoInAndRecordIDIs (List<String> ctlgnos, String recordID);

    public void deleteAllByRecordID (String recordID);

}
