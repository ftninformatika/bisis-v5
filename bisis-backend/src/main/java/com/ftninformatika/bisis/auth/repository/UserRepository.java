package com.ftninformatika.bisis.auth.repository;

import com.ftninformatika.bisis.auth.model.LibrarianUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<LibrarianUser, String> {

    LibrarianUser findByUsername(final String userName);
}
