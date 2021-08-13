package com.ftninformatika.bisis.acquisition.repositories;

import com.ftninformatika.bisis.acquisition.model.Acquisition;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface AcquisitionRepository extends MongoRepository<Acquisition, String> {

    public List<Acquisition> findAcquisitionsByAcquisitionDateBetween(Date start, Date end);
    @Query("{status:{$in:[\"distribution\",\"delivery\",\"processing\"]}}")
    public List<Acquisition> findLastAcquisitionForDistribution(Sort sort);
}
