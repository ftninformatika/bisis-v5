package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.Membership;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "memberships", path = "mongo_repository_memberships")
public interface MembershipRepository extends CoderRepository<Membership> {

   }
