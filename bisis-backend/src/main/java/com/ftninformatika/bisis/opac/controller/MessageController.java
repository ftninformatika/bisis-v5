package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Message;
import com.ftninformatika.bisis.opac.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/{username}")
    public List<Message> getMessages(@PathVariable("username") String username){
        return messageRepository.findByIdSenderOrIdReceiver(username,username);
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

}
