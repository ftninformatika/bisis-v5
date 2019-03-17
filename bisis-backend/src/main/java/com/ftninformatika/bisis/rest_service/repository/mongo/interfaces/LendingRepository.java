package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces;

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

    public List<Lending> findByLendDateBetweenAndCtlgNo(@Param("lendDate") Date start,@Param("lendDate") Date end, @Param("ctlgno") String ctlgno);

    public List<Lending> findByLendDateBetweenAndCtlgNoAndLocation(@Param("lendDate") Date start,@Param("lendDate") Date end, @Param("ctlgno") String ctlgno, @Param("location") String location);

    public List<Lending> findByUserId(@Param("userId") String userId);

    public List<Lending> findByUserIdAndReturnDateIsNull(@Param("userId") String userId);

    public Lending findByCtlgNoAndReturnDateIsNull(@Param("ctlgNo") String ctlgNo);

    public List<Lending> findLendingsByUserIdAndLendDateBetween(@Param("userId") String userId, @Param("lendDate") Date start,@Param("lendDate") Date end);

    public List<Lending> findLendingsByUserIdAndLendDateBetweenAndLocation(@Param("userId") String userId, @Param("lendDate") Date start,@Param("lendDate") Date end,@Param("location") String loc);

    public Lending findByLendDateIsAndCtlgNoIsAndUserIdIs(Date lendDate, String ctlgNo, String userId);

    @Query("{'location': ?3, 'warnings':{ $elemMatch: {'deadline':{ $gte :?0,$lte:?1},'warningType':?2 }}}")
    public List<Lending> findLendingsByWarningHistory(Date startDate, Date endDate, String warningType, String location);

    @Query("{'warnings':{ $elemMatch: {'deadline':{ $gte :?0,$lte:?1},'warningType':?2 }}}")
    public List<Lending> findLendingsByWarningHistory(Date startDate, Date endDate, String warningType);

}
