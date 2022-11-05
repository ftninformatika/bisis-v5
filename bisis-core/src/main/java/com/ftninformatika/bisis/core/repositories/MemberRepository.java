package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Petar on 6/8/2017.
 */
@Repository
@RepositoryRestResource(path = "members_repository")
public interface MemberRepository extends MongoRepository<Member, String>, MemberRepositoryCustom, PagingAndSortingRepository<Member,String> {


    Member getMemberByUserId(String userId);

    Member getMemberByEmail(String email);

    List<Member> findByJmbg(String jmbg);

    List<Member> findByDocNo(String docNo);

    @Query(value = "{'firstName': {$regex: ?0, $options: 'i'}, 'lastName': {$regex: ?1, $options: 'i'}, 'birthday': {$gte: ?2, $lt: ?3}}")
    List<Member> findByFirstNameAndLastNameAndBirthdayIc(String firstName, String lastName, Date birthday, Date birthdayEnd);

    @Query(value = "{'firstName': {$regex: ?0, $options: 'i'}, 'lastName': {$regex: ?1, $options: 'i'}, 'parentName': {$regex: ?2, $options: 'i'}}")
    List<Member> findByFirstNameAndLastNameAndParentNameIc(String firstName, String lastName, String parentName);

    @Query("{'signings':{ $elemMatch: {'signDate':{ $gte :?0,$lte:?1},'location':?2 }}}.count()")
    int getNumberOfMembersByPeriod(Date startDate, Date endDate, String location);

    @Query("{'signings':{ $elemMatch: {'signDate':{ $gte :?0,$lte:?1} }}}.count()")
    int getNumberOfMembersByPeriod(Date startDate, Date endDate);

    List<Member> findByUserIdIn(Collection<String> ids);

    @Query(value = "{'corporateMember.instName': ?0}")
    List<Member> findByCorporateMember(String groupName);

    @Query("{ 'reservations': { $elemMatch: { 'ctlgNo': ?0, 'reservationStatus': ?1 }}}")
    Member getMemberByReservationCtlgNo(@Param("ctlgNo") String invNum, @Param("reservationStatus") String reservationStatus);

    @Query("{ 'reservations': { $elemMatch: {'reservationStatus': ?0, 'reservationDate':{ $gte :?1, $lte:?2}}}}")
    List<Member> findMembersWithReservationsByStatus(ReservationStatus status, Date start, Date end);

    @Query("{ 'reservations': { $elemMatch: {'reservationStatus': ?0}}}")
    List<Member> findMembersWithReservationsByStatus(ReservationStatus status);

    @Query("{ 'reservations': { $elemMatch: {'reservationDate':{ $gte :?0, $lte:?1}}}}")
    List<Member> findMembersWithReservations(Date start, Date end);
}
