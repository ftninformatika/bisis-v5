package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.coders.StatusPrimerka;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestResource(collectionResourceRel = "status", path = "status_primerka")
//@RequestMapping("/coders/status_primerka")
public interface StatusPrimerkaRepository extends MongoRepository<StatusPrimerka, String> {


}
