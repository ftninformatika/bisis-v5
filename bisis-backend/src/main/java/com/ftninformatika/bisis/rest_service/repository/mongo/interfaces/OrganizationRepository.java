package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces;

import com.ftninformatika.bisis.circ.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrganizationRepository extends MongoRepository<Organization, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Organization> getCoders(String libName);
}
