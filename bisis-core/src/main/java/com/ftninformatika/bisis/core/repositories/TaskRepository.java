package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.coders.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by dboberic on 29.3.2018..
 */
public interface TaskRepository extends MongoRepository<Task, String> {

    @Query("{'$or': [{'library':{'$exists': false}},{'library':?0}]}")
    public List<Sublocation> getCoders(String libName);
}
