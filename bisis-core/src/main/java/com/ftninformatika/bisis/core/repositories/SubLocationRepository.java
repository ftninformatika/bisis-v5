package com.ftninformatika.bisis.core.repositories;


import com.ftninformatika.bisis.coders.Sublocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface SubLocationRepository extends MongoRepository<Sublocation,String> {
    public List<Sublocation> findByLibrary(String library);

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Sublocation> getCoders(String libName);


    @Query("{'coder_id': ?0, 'library': ?1}")
    Sublocation getByCoder_Id(String coder_id, String lib);
}
