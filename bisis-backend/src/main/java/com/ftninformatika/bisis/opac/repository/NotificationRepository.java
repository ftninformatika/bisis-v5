package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    Page<Notification> findAllByOrderBySentDateDesc(Pageable pageable);
}
