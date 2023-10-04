package com.ftninformatika.bisisauthentication.repositories;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("librarianAuthenticationRepository")
public interface LibrarianRepository extends MongoRepository<LibrarianDB, String> {
    Optional<LibrarianDB> findByUsername(String username);

    List<LibrarianDB> findByAuthoritiesIn(String[] roles);

    Optional<LibrarianDB> findByEmailAndBiblioteka(String email, String biblioteka);
}
