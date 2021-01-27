package com.ftninformatika.bisis.nabavka.repositories;

import com.ftninformatika.bisis.nabavka.model.Distributor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository extends MongoRepository<Distributor,String> {

    public Distributor getDistributorByPib(String pib);
}
