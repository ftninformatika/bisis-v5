package com.ftninformatika.bisis.opac.service;

import com.ftninformatika.bisis.opac.Message;
import com.ftninformatika.bisis.opac.dto.MemberCardDTO;
import com.ftninformatika.bisis.opac.dto.MessageSenderDTO;
import com.ftninformatika.bisis.opac.repository.MessageRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author marijakovacevic
 */

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    LibraryMemberService libraryMemberService;

    public List<Message> getMessagesByUsername(String username) {
        List<Message> messagesFromUser = messageRepository.findByIdSenderAndIdReceiverOrderByDateAsc(username, "");  // pokriva slucaj i kada poruku salje admin
        List<Message> messagesToUser = messageRepository.findByIdReceiverOrderByDateAsc(username, username);

        for (Message message : messagesFromUser) {
            message.setSeen(true);
            this.messageRepository.save(message);
        }
        List<Message> messages = new ArrayList<>(messagesFromUser);
        messages.addAll(messagesToUser);

        Collections.sort(messages);
        return messages;
    }

    public List<MessageSenderDTO> getSenders(String lib) {
        List<Message> allMessages = messageRepository.findAll();
        List<MessageSenderDTO> senders = new ArrayList<>();
        Set<String> sendersId = new HashSet<>();

        for (Message m : allMessages) {
            if (m.getIdReceiver().equals("") && !sendersId.contains(m.getIdSender())) {
                MessageSenderDTO messageSenderDTO = createMessageSenderDTO(lib, m);
                sendersId.add(m.getIdSender());
                senders.add(messageSenderDTO);
            }
        }
        Collections.sort(senders);
        return senders;
    }

    private MessageSenderDTO createMessageSenderDTO(String lib, Message m) {
        MemberCardDTO memberCardDTO = libraryMemberService.getMemberCard(lib, m.getIdSender());
        if (memberCardDTO != null) {
            Message message = messageRepository.findFirstByIdSenderAndIdReceiverOrIdReceiverOrderByDateDesc(m.getIdSender(), "",
                    m.getIdSender());

            List<Message> messages = messageRepository.findAllByIdSenderAndSeenFalse(m.getIdSender());
            return new MessageSenderDTO(memberCardDTO, message, messages.size());
        }
        return null;
    }
}
