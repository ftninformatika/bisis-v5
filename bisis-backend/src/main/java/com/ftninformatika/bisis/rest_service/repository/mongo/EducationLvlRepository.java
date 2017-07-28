package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.circ.EducationLvl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EducationLvlRepository extends MongoRepository<EducationLvl, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<EducationLvl> getCoders(String libName);
}
