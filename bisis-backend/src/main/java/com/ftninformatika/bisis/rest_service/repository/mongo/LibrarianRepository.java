package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.librarian.Librarian;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Petar on 6/20/2017.
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "librarians")
public interface LibrarianRepository extends MongoRepository<Librarian, String> {

    public Librarian getByUsername(@Param("username") String username);
}
