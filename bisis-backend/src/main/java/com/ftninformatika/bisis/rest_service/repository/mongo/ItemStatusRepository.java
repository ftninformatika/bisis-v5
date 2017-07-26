package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface ItemStatusRepository extends MongoRepository<ItemStatus, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<ItemStatus> getStatuses(String libName);
}
