package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.RecordType;
import org.springframework.stereotype.Repository;

@Repository("recordTypeJPARepository")
public interface RecordTypeRepository extends CoderRepository<RecordType> {
}
