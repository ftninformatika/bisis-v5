package com.ftninformatika.bisis.core.repositories;


import com.ftninformatika.bisis.coders.Sublocation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface SubLocationRepository extends CoderRepository<Sublocation> {
    public List<Sublocation> findByLibrary(String library);



    @Query("{'coder_id': ?0, 'library': ?1}")
    Sublocation getByCoder_Id(String coder_id, String lib);
}
