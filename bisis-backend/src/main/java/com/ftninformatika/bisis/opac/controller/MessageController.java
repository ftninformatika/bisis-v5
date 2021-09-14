package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Message;
import com.ftninformatika.bisis.opac.dto.MemberCardDTO;
import com.ftninformatika.bisis.opac.dto.MessageSenderDTO;
import com.ftninformatika.bisis.opac.repository.MessageRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    LibraryMemberService libraryMemberService;

    @GetMapping("/{username}")
    public List<Message> getMessages(@PathVariable("username") String username){
        List<Message> messages = messageRepository.findByIdSenderOrIdReceiverOrderByDateAsc(username,username);
        for (Message message : messages) {
            message.setSeen(true);
            this.messageRepository.save(message);
        }
        return messages;
    }

    @PostMapping("/add")
    public ResponseEntity sendMessages(@RequestBody Message message){
        try{
           Message m = messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(m);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all-senders")
    public ResponseEntity<List<MessageSenderDTO>> getSenders(@RequestHeader("Library") String lib){
        String bibliotekar = "bibliotekar@" + lib;
        Set<String> sendersId = new HashSet<>();
        List<MessageSenderDTO> senders = new ArrayList<>();
        List<Message> allMessages = messageRepository.findAll();

        for (Message m : allMessages) {
            if (!m.getIdSender().equals(bibliotekar) && !sendersId.contains(m.getIdSender())){
                MemberCardDTO memberCardDTO = libraryMemberService.getMemberCard(lib, m.getIdSender());
                if (memberCardDTO != null) {
                    Message message = messageRepository.findFirstByIdSenderOrIdReceiverOrderByDateDesc(m.getIdSender(),
                            m.getIdSender());
                    sendersId.add(m.getIdSender());
                    MessageSenderDTO messageSenderDTO = new MessageSenderDTO(memberCardDTO, message);
                    senders.add(messageSenderDTO);
                }
            }
        }
        Collections.sort(senders);
        return new ResponseEntity<>(senders, HttpStatus.OK);
    }


}
