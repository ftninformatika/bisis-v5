package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.SerialType;
import org.springframework.stereotype.Repository;

@Repository("serialTypeJPARepository")
public interface SerialTypeRepository extends CoderRepository<SerialType> {
}
