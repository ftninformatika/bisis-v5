package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    Page<Event> findAllByOrderByDateDesc(Pageable pageable);

    Event getBy_id(String _id);

    @Query("{$or: [{'title': {$regex : ?0, $options: 'i'}}, {'content': {$regex : ?0, $options: 'i'}}]}.sort({date: -1})")
    Page<Event> searchTextOnly(String title, Pageable pageable);

    @Query("{$and: [" +
            "{$or: [{'title': {$regex : ?0, $options: 'i'}}, {'content': {$regex : ?0, $options: 'i'}}]}, " +
            "{'date': {$gte :?1}}" +
            "]}.order({date: -1})")
    Page<Event> searchFromDateOnly(String title, Date startDate, Pageable pageable);

    @Query("{$and: [" +
            "{$or: [{'title': {$regex : ?0, $options: 'i'}}, {'content': {$regex : ?0, $options: 'i'}}]}, " +
            "{'date': { $gte :?1, $lte:?2}}" +
            "]}.sort({date: -1})")
    Page<Event> search(String title, Date startDate, Date endDate, Pageable pageable);

    List<Event> findAllByDateAfterOrderByDateAsc(Date date);

}
