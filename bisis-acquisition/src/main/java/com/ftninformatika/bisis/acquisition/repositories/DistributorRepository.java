package com.ftninformatika.bisis.acquisition.repositories;

import com.ftninformatika.bisis.acquisition.model.Distributor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository extends MongoRepository<Distributor,String> {

    public Distributor getDistributorByPib(String pib);
}
