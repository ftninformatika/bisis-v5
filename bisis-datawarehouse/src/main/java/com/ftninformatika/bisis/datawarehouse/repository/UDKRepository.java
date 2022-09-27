package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Udk;
import org.springframework.stereotype.Repository;

@Repository("udkJPARepository")
public interface UDKRepository extends CoderRepository<Udk> {
}
