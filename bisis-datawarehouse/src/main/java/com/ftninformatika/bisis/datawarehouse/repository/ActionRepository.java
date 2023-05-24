package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Action;
import org.springframework.stereotype.Repository;

@Repository("acquisitionJPARepository")
public interface ActionRepository extends CoderRepository<Action>{


}
