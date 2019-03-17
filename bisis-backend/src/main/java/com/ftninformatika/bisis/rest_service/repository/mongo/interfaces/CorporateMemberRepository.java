package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces;

import com.ftninformatika.bisis.circ.CorporateMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Petar on 8/30/2017.
 */
public interface CorporateMemberRepository extends MongoRepository<CorporateMember, String>{

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<CorporateMember> getCoders(String libName);

    public CorporateMember findByUserId(String userId);

}
