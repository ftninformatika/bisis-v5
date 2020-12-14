package com.ftninformatika.bisisris.repositories;

import com.ftninformatika.bisis.coders.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface LocationRepository extends MongoRepository<Location,String> {
    @Query("{'library':?0, 'coder_id':?1}")
    public Location findCoder(String library, String coder_id);
    public List<Location> findByLibrary(String library);

}
