package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.LendingAction;
import org.springframework.stereotype.Repository;

@Repository("lendingActionJPARepository")
public interface LendingActionRepository extends CoderRepository<LendingAction> {
}
