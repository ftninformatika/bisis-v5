package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.reservations.Reservations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReservationsRepository extends MongoRepository<Reservations, String> {
    Reservations findByCtlgNo(String ctlgNo);

    @Query("{'ctlgNo':{$in : ?0}, length:{$size:'reservations'}, $sort:{length:-1}, $limit:1}")
    Reservations getReservationsByMaxReservationsSize(Set<String> ctlgNos);

}
