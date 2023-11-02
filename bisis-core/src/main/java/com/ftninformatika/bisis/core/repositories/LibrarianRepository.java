package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Petar on 6/20/2017.
 */
@Repository
public interface LibrarianRepository extends MongoRepository<LibrarianDB, String>, PagingAndSortingRepository<LibrarianDB, String> {
    LibrarianDB getByUsername(@Param("username") String username);
    List<LibrarianDB> getLibrariansByBiblioteka(@Param("library") String library,Sort sort);
    Optional<LibrarianDB> findByEmailAndBiblioteka(String email, String biblioteka);
}
