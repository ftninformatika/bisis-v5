package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.CircLocation;
import org.springframework.stereotype.Repository;

@Repository("circLocationJPARepository")
public interface CircLocationRepository extends CoderRepository<CircLocation> {
}
