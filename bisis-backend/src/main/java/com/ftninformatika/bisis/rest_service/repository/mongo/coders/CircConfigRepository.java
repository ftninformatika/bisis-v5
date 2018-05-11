package com.ftninformatika.bisis.rest_service.repository.mongo.coders;

import com.ftninformatika.bisis.circ.CircConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Petar on 8/29/2017.
 */
@RepositoryRestResource(collectionResourceRel = "circs", path = "circ_configs")
public interface CircConfigRepository extends MongoRepository<CircConfig, String> {

    public CircConfig findByLibrary(@Param("libname") String libName);
}
