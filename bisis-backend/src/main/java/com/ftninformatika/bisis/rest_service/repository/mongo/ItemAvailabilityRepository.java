package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.records.ItemAvailability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Petar on 10/19/2017.
 */
public interface ItemAvailabilityRepository extends MongoRepository<ItemAvailability, String>, PagingAndSortingRepository<ItemAvailability,String>{

    public List<ItemAvailability> findByRecordID(String recId);

    public ItemAvailability getByCtlgNo(String ctlno);

}
