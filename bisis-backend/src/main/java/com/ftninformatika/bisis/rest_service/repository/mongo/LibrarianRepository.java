package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Petar on 6/20/2017.
 */
@RepositoryRestResource(collectionResourceRel = "librarians", path = "mongo_repository_librarians")
public interface LibrarianRepository extends MongoRepository<LibrarianDTO, String> {

    public LibrarianDTO getByUsername(@Param("username") String username);

    public List<LibrarianDTO> getLibrariansByBiblioteka(@Param("library") String library);
    public LibrarianDTO getByEmail(String email);
}
