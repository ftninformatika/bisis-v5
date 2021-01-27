package com.ftninformatika.bisis.nabavka.repositories;

import com.ftninformatika.bisis.nabavka.model.Desideratum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesideratumRepository extends MongoRepository<Desideratum, String> {

    public Desideratum findByIsbn(String isbn);
}
