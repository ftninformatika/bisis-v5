package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event,String> {

    public List<Event> findEventByDateAfterOrderByDateDesc(Date today);
    public Event getBy_id(String _id);
}
