package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.MembershipType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MembershipTypeRepository extends MongoRepository<MembershipType, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<MembershipType> getCoders(String libName);
}
