package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Location;
import org.springframework.stereotype.Repository;

@Repository("locationJPARepository")
public interface LocationRepository extends CoderRepository<Location> {
}
