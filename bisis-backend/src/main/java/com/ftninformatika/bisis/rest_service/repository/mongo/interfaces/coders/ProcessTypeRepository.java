package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.coders;

import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Petar on 7/26/2017.
 */
@RepositoryRestResource(collectionResourceRel = "processTypes", path = "process_types")
public interface ProcessTypeRepository extends MongoRepository<ProcessTypeDTO, String>{

    public List<ProcessTypeDTO> getProcessTypesByLibNameIsNullOrLibName(@Param("libName") String libName);
    public ProcessTypeDTO findByName (String ptName);
    public ProcessTypeDTO findByNameAndLibName(@Param("name") String name,@Param("libName") String libName);
}
