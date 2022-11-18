package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("membershipJPARepository")
public interface MembershipRepository extends JpaRepository<Membership,Integer> {
    @Transactional
    void deleteByLibrary(String library);
    List<Membership> findByMember_Id(String memberId);
}
