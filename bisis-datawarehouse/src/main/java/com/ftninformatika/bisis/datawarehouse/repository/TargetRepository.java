package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Target;
import org.springframework.stereotype.Repository;

@Repository("targetJPARepository")
public interface TargetRepository extends CoderRepository<Target> {
}
