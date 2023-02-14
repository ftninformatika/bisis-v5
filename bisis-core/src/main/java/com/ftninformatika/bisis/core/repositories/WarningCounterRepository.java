package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.WarningCounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningCounterRepository extends CoderRepository<WarningCounter> {

}
