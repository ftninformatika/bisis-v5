package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Member;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Petar on 6/8/2017.
 */
@Repository
@RepositoryRestResource( path = "members_repository")
public interface MemberRepository extends MongoRepository<Member,String>, MemberRepositoryCustom {


    Member getMemberByUserId(String userId);

    Member getMemberByEmail(String email);

    @Query("{'signings':{ $elemMatch: {'signDate':{ $gte :?0,$lte:?1},'location':?3 }}}.count()")
    int getNumberOfMembersByPeriod(Date startDate, Date endDate, String location);

    @Query("{'signings':{ $elemMatch: {'signDate':{ $gte :?0,$lte:?1} }}}.count()")
    int getNumberOfMembersByPeriod(Date startDate, Date endDate);

    List<Member> findByUserIdIn(Collection<String> ids);

    @Query(value =  "{'corporateMember.instName': ?0}")
    List<Member> findByCorporateMember(String groupName);

}