package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Acquisition;
import org.springframework.stereotype.Repository;

@Repository("acquisitionJPARepository")
public interface AcquisitionRepository extends CoderRepository<Acquisition>{
}
