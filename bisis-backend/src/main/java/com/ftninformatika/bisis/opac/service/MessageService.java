package com.ftninformatika.bisis.opac.service;

import com.ftninformatika.bisis.opac.Message;
import com.ftninformatika.bisis.opac.admin.dto.MessageDTO;
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

    public List<MessageDTO> getMessagesByUsername(String username, String library) {
        List<Message> messagesFromUser = messageRepository.findByIdSenderAndIdReceiverOrderByDateAsc(username, "");  // pokriva slucaj i kada poruku salje admin
        setSeen(messagesFromUser);
        List<MessageDTO> messagesFromUserDTO = getMessageDTOS(messagesFromUser);

        List<Message> messagesToUser = messageRepository.findByIdReceiverOrderByDateAsc(username);
        List<MessageDTO> messagesToUserDTO = setSenderNameAndSurname(messagesToUser, library);

        List<MessageDTO> messages = new ArrayList<>(messagesFromUserDTO);
        messages.addAll(messagesToUserDTO);

        Collections.sort(messages);
        return messages;
    }

    private void setSeen(List<Message> messagesFromUser) {
        for (Message message : messagesFromUser) {
            message.setSeen(true);
            this.messageRepository.save(message);
        }
    }

    private List<MessageDTO> setSenderNameAndSurname(List<Message> messagesToUser, String library) {
        List<MessageDTO> messagesToUserWithSender = new ArrayList<>();
        for (Message message : messagesToUser) {
            MemberCardDTO memberCardDTO = libraryMemberService.getMemberCard(library, message.getIdSender());
            MessageDTO messageDTO = new MessageDTO(message, memberCardDTO.getFirstName(), memberCardDTO.getLastName());
            messagesToUserWithSender.add(messageDTO);
        }
        return messagesToUserWithSender;
    }

    private List<MessageDTO> getMessageDTOS(List<Message> messagesFromUser) {
        List<MessageDTO> messagesFromUserDTO = new ArrayList<>();
        for (Message message : messagesFromUser) {
            MessageDTO messageDTO = new MessageDTO(message);
            messagesFromUserDTO.add(messageDTO);
        }
        return messagesFromUserDTO;
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
