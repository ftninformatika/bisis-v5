package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.librarian.db.LibrarianRoleDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRoleRepository extends MongoRepository<LibrarianRoleDB, String> {
}
