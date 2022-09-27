package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Status;
import org.springframework.stereotype.Repository;

@Repository("statusJPARepository")
public interface StatusRepository extends CoderRepository<Status> {
}
