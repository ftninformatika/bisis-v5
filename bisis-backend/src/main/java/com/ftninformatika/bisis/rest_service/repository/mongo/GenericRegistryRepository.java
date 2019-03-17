package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.registry.GenericRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GenericRegistryRepository extends MongoRepository<GenericRegistry, String> {

    public List<GenericRegistry> findByCode (Integer registryCode);
    public Boolean deleteBy_id (String _id);
}
