package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.records.DocFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Petar on 1/24/2018.
 */
public interface DocFileRepository extends MongoRepository<DocFile, String>{
}
