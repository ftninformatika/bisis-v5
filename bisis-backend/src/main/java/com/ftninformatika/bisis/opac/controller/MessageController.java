package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Message;
import com.ftninformatika.bisis.opac.admin.dto.MessageDTO;
import com.ftninformatika.bisis.opac.dto.MessageSenderDTO;
import com.ftninformatika.bisis.opac.repository.MessageRepository;
import com.ftninformatika.bisis.opac.service.MessageService;
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
    MessageService messageService;

    @GetMapping("/{username}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("username") String username, @RequestHeader("Library") String lib) {
        List<MessageDTO> messages = messageService.getMessagesByUsername(username, lib);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> sendMessages(@RequestBody Message message) {
        try {
            Message savedMessage = messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all-senders")
    public ResponseEntity<List<MessageSenderDTO>> getSenders(@RequestHeader("Library") String lib) {
        List<MessageSenderDTO> messageSenderDTOS = this.messageService.getSenders(lib);
        return new ResponseEntity<>(messageSenderDTOS, HttpStatus.OK);
    }
}
