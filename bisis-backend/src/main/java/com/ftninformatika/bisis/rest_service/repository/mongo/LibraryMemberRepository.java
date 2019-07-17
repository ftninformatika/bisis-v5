package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.opac2.members.LibraryMember;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Petar on 10/16/2017.
 */
public interface LibraryMemberRepository extends MongoRepository<LibraryMember, String> {

    LibraryMember findByUsername(String username);
    LibraryMember findByPasswordResetString(String passwordResetString);
    LibraryMember findByActivationToken(String activationToken);
}
