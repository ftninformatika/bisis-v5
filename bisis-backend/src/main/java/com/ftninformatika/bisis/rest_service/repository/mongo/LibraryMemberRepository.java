package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.circ.pojo.LibraryMember;
import com.mongodb.Mongo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Petar on 10/16/2017.
 */
public interface LibraryMemberRepository extends MongoRepository<LibraryMember, String> {

    LibraryMember findByUsername(String username);
}
