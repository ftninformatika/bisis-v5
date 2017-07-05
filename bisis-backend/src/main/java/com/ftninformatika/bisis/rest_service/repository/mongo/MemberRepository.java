package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.members.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Petar on 6/8/2017.
 */
@RepositoryRestResource(collectionResourceRel = "gbns.members", path = "members")
@RequestMapping("/members")
public interface MemberRepository extends MongoRepository<Member,String> {


    @Query("{ 'lastName' : ?0 }, { 'lending':0, 'signing':0}")
    List<Member> byLastName(@Param("lastName") String lastName);

    @Query("{ }, { 'lending':0, 'signing':0}")
    List<Member> getUsers();

    @RestResource( path = "cnt", rel = "cnt")
    int countAllByGroups(@Param("groups") int groups);
}
