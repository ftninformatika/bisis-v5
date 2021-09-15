package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    Page<Event> findAllByOrderByDateDesc(Pageable pageable);

    Event getBy_id(String _id);

    // todo: dodati sort $orderby: { date : -1 }

    @Query("{$or: [{'title': {$regex : ?0}}, {'content': {$regex : ?0}}]}")
    Page<Event> searchTextOnly(String title, Pageable pageable);

    @Query("{$and: [" +
            "{$or: [{'title': {$regex : ?0}}, {'content': {$regex : ?0}}]}, " +
            "{'date': {$gte :?1}}" +
            "]}")
    Page<Event> searchFromDateOnly(String title, Date startDate, Pageable pageable);

    @Query("{$and: [" +
            "{$or: [{'title': {$regex : ?0}}, {'content': {$regex : ?0}}]}, " +
            "{'date': { $gte :?1, $lte:?2}}" +
            "]}")
    Page<Event> search(String title, Date startDate, Date endDate, Pageable pageable);

}
