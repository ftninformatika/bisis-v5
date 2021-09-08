package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {
    public List<Message> findByIdSenderOrIdReceiverOrderByDateAsc(String sender,String receiver);
    Message findFirstByIdSenderOrIdReceiverOrderByDateDesc(String idSender, String idReceiver);
}
