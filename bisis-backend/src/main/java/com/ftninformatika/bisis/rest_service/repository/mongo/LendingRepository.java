package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

/**
 * Created by Petar on 8/28/2017.
 */
@RepositoryRestResource(collectionResourceRel = "lendings", path = "mongo_repository_lendings")
public interface LendingRepository extends MongoRepository<Lending, String>,LendingRepositoryCustom{

    public List<Lending> findByUserId(@Param("userId") String userId);

    public List<Lending> findByUserIdAndReturnDateIsNull(@Param("userId") String userId);

}
