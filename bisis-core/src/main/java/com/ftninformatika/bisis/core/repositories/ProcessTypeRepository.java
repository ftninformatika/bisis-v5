package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Petar on 7/26/2017.
 */
@RepositoryRestResource(collectionResourceRel = "processTypes", path = "process_types")
public interface ProcessTypeRepository extends MongoRepository<ProcessTypeDB, String>{

    public List<ProcessTypeDB> getProcessTypesByLibNameIsNullOrLibName(@Param("libName") String libName);
    public ProcessTypeDB findByName(String ptName);
    public ProcessTypeDB findByNameAndLibName(@Param("name") String name, @Param("libName") String libName);
}
