package com.ftninformatika.bisis.nabavka.repositories;


import com.ftninformatika.bisis.nabavka.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends MongoRepository<Offer,String> {

    public Offer findOfferByDistributor(String pib);
}

