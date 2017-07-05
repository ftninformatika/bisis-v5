package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Petar on 6/30/2017.
 */
@RepositoryRestResource(collectionResourceRel = "configs", path = "configs")
public interface LibraryConfigurationInterface extends MongoRepository<LibraryConfiguration, String> {

    @Query("{ 'libraryName': ?0 }")
    public LibraryConfiguration getByLibraryName(@Param("libName") String libName);
}
