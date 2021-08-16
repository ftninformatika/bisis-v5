package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Petar on 6/20/2017.
 */
@Repository
public interface LibrarianRepository extends MongoRepository<LibrarianDB, String> {
    public LibrarianDB getByUsername(@Param("username") String username);
    public List<LibrarianDB> getLibrariansByBiblioteka(@Param("library") String library);
}
