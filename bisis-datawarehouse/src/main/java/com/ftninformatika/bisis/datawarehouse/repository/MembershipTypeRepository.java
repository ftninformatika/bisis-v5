package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.MembershipType;
import org.springframework.stereotype.Repository;

@Repository("membershipTypeJPARepository")
public interface MembershipTypeRepository extends CoderRepository<MembershipType> {
}
