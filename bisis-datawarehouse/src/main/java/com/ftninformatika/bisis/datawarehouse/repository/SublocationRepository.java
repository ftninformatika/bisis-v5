package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Sublocation;
import org.springframework.stereotype.Repository;

@Repository("sublocationJPARepository")
public interface SublocationRepository extends CoderRepository<Sublocation> {
}
