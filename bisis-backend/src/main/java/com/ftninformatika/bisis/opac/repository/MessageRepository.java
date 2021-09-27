package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {
    List<Message> findByIdSenderAndIdReceiverOrderByDateAsc(String sender, String receiver);
    List<Message> findByIdReceiverOrderByDateAsc(String sender, String receiver);

    Message findFirstByIdSenderAndIdReceiverOrIdReceiverOrderByDateDesc(String idSender, String noReceiver, String idReceiver);
    List<Message> findAllByIdSenderAndSeenFalse(String idSender);

}
