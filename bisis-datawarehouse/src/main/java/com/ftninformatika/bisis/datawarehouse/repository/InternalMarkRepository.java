package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.InternalMark;
import org.springframework.stereotype.Repository;

@Repository("internalMarkJPARepository")
public interface InternalMarkRepository extends CoderRepository<InternalMark>{
}
