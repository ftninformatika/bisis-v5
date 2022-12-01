package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.CorporateMember;
import org.springframework.stereotype.Repository;

@Repository("corporateMemberJPARepository")
public interface CorporateMemberRepository extends CoderRepository<CorporateMember> {
}

