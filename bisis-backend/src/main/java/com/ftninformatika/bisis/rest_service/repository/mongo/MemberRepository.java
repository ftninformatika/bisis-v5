package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.circ.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Petar on 6/8/2017.
 */

public interface MemberRepository extends MongoRepository<Member,String> {


     Member getMemberByUserId(String userId);

     Member getMemberByEmail(String email);


}
