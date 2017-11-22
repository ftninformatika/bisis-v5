package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Petar on 6/8/2017.
 */
@Repository
public interface MemberRepository extends MongoRepository<Member,String>, MemberRepositoryCustom {


     Member getMemberByUserId(String userId);

     Member getMemberByEmail(String email);

     @Query("{'signings':{ $elemMatch: {'signDate':{ $gte :?0,$lte:?1},'location':?3 }}}.count()")
     int getNumberOfMembersByPeriod(Date startDate,Date endDate,String location);

    @Query("{'signings':{ $elemMatch: {'signDate':{ $gte :?0,$lte:?1} }}}.count()")
    int getNumberOfMembersByPeriod(Date startDate,Date endDate);


    /*
    db.gbns_com_members.find({ "signings":{$elemMatch:{
            "signDate":
            {$gte:new ISODate("2010-11-27"),$lte:new ISODate("2011-11-27")}
        }}},{"firstName":1,"userCategory.description":1,
    "signings":{
        $elemMatch:{
            "signDate":
            {$gte:new ISODate("2010-11-27"),$lte:new ISODate("2011-11-27")}
        }
    }

})
*/
      @Query("{'signings':{$elemMatch:{'signDate':{$gte:?0,$lte:?0}}}}," +
            "{'userId':1,'firstName':1,'lastName':1,'address':1,'zip':1,'city':1,'docNo':1,'docCity':1," +
            "'jmbg':1,'userCategory.description':1,'signings':{$elemMatch:{'signDate':{$gte:?0,$lte:?0}}}}")
      List<Member> getMembersByCategories(Date startDate);
}
