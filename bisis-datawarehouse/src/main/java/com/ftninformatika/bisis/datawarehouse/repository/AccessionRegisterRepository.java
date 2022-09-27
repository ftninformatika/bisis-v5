package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.AccessionRegister;
import org.springframework.stereotype.Repository;

@Repository("accessionRegisterJPARepository")
public interface AccessionRegisterRepository extends CoderRepository<AccessionRegister> {
}
