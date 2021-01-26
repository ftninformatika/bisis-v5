package com.ftninformatika.bisisauthentication.repositories;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("libraryConfigurationAuthenticationRepository")
public interface LibraryConfigurationRepository extends MongoRepository<LibraryConfiguration, String> {
}
