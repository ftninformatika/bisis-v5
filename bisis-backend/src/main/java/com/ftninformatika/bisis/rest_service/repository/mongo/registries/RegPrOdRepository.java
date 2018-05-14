package com.ftninformatika.bisis.rest_service.repository.mongo.registries;

import com.ftninformatika.bisis.registry.RegPrOd;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface RegPrOdRepository extends MongoRepository<RegPrOd, String > {
}
