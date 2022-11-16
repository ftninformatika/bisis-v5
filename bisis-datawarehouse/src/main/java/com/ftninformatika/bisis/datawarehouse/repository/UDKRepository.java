package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Udk;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("udksJPARepository")
public interface UDKRepository extends CoderRepository<Udk> {

    List<Udk> findByIdIn(List udks);
}
