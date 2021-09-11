package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Date;

@Repository
public interface EventRepository extends MongoRepository<Event,String> {

    public Page<Event> findEventByDateAfterOrderByDateDesc(Date today, Pageable pageable);
    public Event getBy_id(String _id);
}
