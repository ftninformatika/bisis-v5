package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("memberJPARepository")
public interface MemberRepository extends JpaRepository<Member,String> {
    @Transactional
    public void deleteAllByLibrary(String library);
}
