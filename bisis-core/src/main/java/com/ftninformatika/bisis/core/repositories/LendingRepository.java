package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.Lending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by Petar on 8/28/2017.
 */
@RepositoryRestResource(collectionResourceRel = "lendings")
public interface LendingRepository extends MongoRepository<Lending, String>, PagingAndSortingRepository<Lending,String>,LendingRepositoryCustom{

    public List<Lending> findByLendDateBetweenAndCtlgNo(@Param("lendDate") Date start,@Param("lendDate") Date end, @Param("ctlgno") String ctlgno);

    public List<Lending> findByLendDateBetweenAndCtlgNoAndLocation(@Param("lendDate") Date start,@Param("lendDate") Date end, @Param("ctlgno") String ctlgno, @Param("location") String location);

    public List<Lending> findByUserId(@Param("userId") String userId);

    public List<Lending> findByUserIdAndReturnDateIsNull(@Param("userId") String userId);

    public Lending findByCtlgNoAndReturnDateIsNull(@Param("ctlgNo") String ctlgNo);
    public Lending findByCtlgNoAndLendDateBeforeAndReturnDateAfter(@Param("ctlgNo") String ctlgNo, @Param("lendDate") Date lendDate, @Param("returnDate") Date returnDate);
    public Lending findByCtlgNoAndLendDateBeforeAndReturnDateIsNull(@Param("ctlgNo") String ctlgNo, @Param("lendDate") Date revisionStart);


    public List<Lending> findLendingsByUserIdAndLendDateBetween(@Param("userId") String userId, @Param("lendDate") Date start,@Param("lendDate") Date end);

    public List<Lending> findLendingsByUserIdAndLendDateBetweenAndLocation(@Param("userId") String userId, @Param("lendDate") Date start,@Param("lendDate") Date end,@Param("location") String loc);

    public Lending findByLendDateIsAndCtlgNoIsAndUserIdIs(Date lendDate, String ctlgNo, String userId);

    @Query("{'location': ?3, 'warnings':{ $elemMatch: {'deadline':{ $gte :?0,$lte:?1},'warningType':?2 }}}")
    public List<Lending> findLendingsByWarningHistoryWithLocation(Date startDate, Date endDate, String warningType, String location);

    @Query("{'warnings':{ $elemMatch: {'deadline':{ $gte :?0,$lte:?1},'warningType':?2 }}}")
    public List<Lending> findLendingsByWarningHistory(Date startDate, Date endDate, String warningType);

    @Query("{'ctlgNo':{$in : ?0}}")
    List<Lending> getLendingsForCtlgNoList(List ctlgNos);

    public List<Lending> findLendingsByDeadlineBetweenAndReturnDateIsNull(Date start, Date end);

    public Page<Lending> findByLendDateAfter(LocalDate lendingDate, Pageable pageable);

}
