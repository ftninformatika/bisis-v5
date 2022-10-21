package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("membershipJPARepository")
public interface MembershipRepository extends JpaRepository<Membership,Integer> {

    void deleteByLibrary(String library);

}
