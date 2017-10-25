package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.models.coders.Binding;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BindingRepository extends MongoRepository<Binding, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Binding> getCoders(String libName);

    @Query("{'$or': [{'library':?0},{'coder_id':?1}]}")
    public Binding getCoder(String library, String code);
}
