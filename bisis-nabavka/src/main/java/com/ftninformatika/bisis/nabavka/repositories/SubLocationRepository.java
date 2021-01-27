package com.ftninformatika.bisis.nabavka.repositories;


import com.ftninformatika.bisis.coders.Sublocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface SubLocationRepository extends MongoRepository<Sublocation,String> {
    public List<Sublocation> findByLibrary(String library);
}
