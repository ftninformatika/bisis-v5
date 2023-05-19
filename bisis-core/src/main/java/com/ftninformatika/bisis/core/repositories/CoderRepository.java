package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.Coder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CoderRepository <T extends Coder> extends MongoRepository<T,String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<T> getCoders(String libName);

    @Query("{'library':?0, 'coder_id':?1}")
    public T findCoder(String library, String coder_id);

    @Query(value = "{'library':?0, '_id':?1}", delete = true)
    public void deleteCoder(String library, String coder_id);

}
