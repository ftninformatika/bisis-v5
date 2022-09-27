package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Gender;
import org.springframework.stereotype.Repository;

@Repository("genderJPARepository")
public interface GenderRepository extends CoderRepository<Gender> {
}
