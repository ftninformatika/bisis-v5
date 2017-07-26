package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.librarian.ProcessType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Petar on 7/26/2017.
 */
@RepositoryRestResource(collectionResourceRel = "processTypes", path = "process_types")
public interface ProcessTypeRepository extends MongoRepository<ProcessType, String>{

    public List<ProcessType> getProcessTypesByLibNameIsNullOrLibName(@Param("libName") String libName);

}
