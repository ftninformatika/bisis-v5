package com.ftninformatika.bisis.nabavka.repositories;

import com.ftninformatika.bisis.coders.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface LocationRepository extends MongoRepository<Location,String> {
    public List<Location> findByLibrary(String library);
    @Query("{'library':?0, 'coder_id':?1}")
    public Location findCoder(String library,String coder_id);
}
