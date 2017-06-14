package com.ftninformatika.bisis.rest_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.ftninformatika.bisis.rest_service.bisis4_model.Libraries;

/**
 * Created by Petar on 6/9/2017.
 */
@RepositoryRestResource(collectionResourceRel = "libraries", path = "libraries")

public interface LibrariesRepository extends MongoRepository<Libraries, String> {


}
