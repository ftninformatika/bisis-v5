package com.ftninformatika.bisisauthentication.repositories;

import com.ftninformatika.bisis.circ.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("memberAuthenticationRepository")
public interface MemberRepository extends MongoRepository<Member, String> {
}
