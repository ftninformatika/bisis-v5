package com.ftninformatika.bisisauthentication.repositories;

import com.ftninformatika.bisis.opac.members.LibraryMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("libraryMemberAuthenticationRepository")
public interface LibraryMemberRepository extends MongoRepository<LibraryMember, String> {
    Optional<LibraryMember> findByUsername(String username);
    Optional<LibraryMember> findByRefreshToken(String token);
}
