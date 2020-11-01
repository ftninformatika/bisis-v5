package com.ftninformatika.bisisris.repositories;

import com.ftninformatika.bisis.coders.Sublocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface SubLocationRepository extends MongoRepository<Sublocation,String> {
    @Query("{'library':?0, 'coder_id':?1}")
    public Sublocation findCoder(String library, String coder_id);
}
