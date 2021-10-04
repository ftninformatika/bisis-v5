package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
