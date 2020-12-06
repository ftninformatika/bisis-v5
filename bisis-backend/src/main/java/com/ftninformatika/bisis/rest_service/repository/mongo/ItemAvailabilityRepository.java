package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.records.ItemAvailability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;

/**
 * Created by Petar on 10/19/2017.
 */
public interface ItemAvailabilityRepository extends MongoRepository<ItemAvailability, String>, PagingAndSortingRepository<ItemAvailability,String>{

    List<ItemAvailability> findByRecordID(String recId);
    ItemAvailability getByCtlgNo(String ctlno);
    List<ItemAvailability> findAllByCtlgNo (String ctlgNo);
    List<ItemAvailability> findAllByCtlgNoIsIn(List<String> ctglNos);
    void deleteByCtlgNoIn (List<String> ctlgnos);
    void deleteByCtlgNoInAndRecordIDIs (List<String> ctlgnos, String recordID);
    void deleteAllByRecordID (String recordID);
    List<ItemAvailability> findByInventoryIdAndBorrowedIsTrue(String inventoryId);

}
