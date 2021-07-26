package com.ftninformatika.bisis.core.repositories;

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
    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    List<Location> getCoders(String libName);

    Location getByDescriptionAndLibrary(String desc, String lib);

    @Query("{'coder_id': ?0, 'library': ?1}")
    Location getByCoder_Id(String coder_id, String lib);
}
