package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.librarian.web.Librarian;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.Message;
import com.ftninformatika.bisis.opac.admin.dto.MessageDTO;
import com.ftninformatika.bisis.opac.dto.MessageSenderDTO;
import com.ftninformatika.bisis.opac.repository.MessageRepository;
import com.ftninformatika.bisis.opac.service.MessageService;
import com.ftninformatika.bisis.opac.service.NotificationService;
import com.ftninformatika.bisis.rest_service.Texts;
import com.ftninformatika.bisis.rest_service.service.implementations.EmailService;
import com.ftninformatika.utils.validators.memberdata.DataErrors;
import com.ftninformatika.utils.validators.memberdata.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/messages")
@PropertySource(value = "classpath:notification.properties",encoding = "UTF-8")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    EmailService emailService;

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Value("${message.title}")
    String messageTitle;

    @Value("${message.content}")
    String messageContent;

    @GetMapping("/{username}")
    public List<Message> getMessagesMobile(@PathVariable("username") String username) {
        return messageRepository.findByIdSenderOrIdReceiver(username, username);
    }

    @GetMapping("all/{username}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable("username") String username, @RequestHeader("Library") String lib) {
        List<MessageDTO> messages = messageService.getMessagesByUsername(username, lib);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Message> sendMessages(@RequestBody Message message, @RequestHeader("Library") String lib) {
        try {
            Message savedMessage = messageRepository.save(message);
            if(message.getIdReceiver() != null && !message.getIdReceiver().equals("")){
                notificationService.sendMessageToUsername(message.getIdReceiver(),messageTitle,messageContent,"message");
            } else {
                String opacMail = getOpacMail(lib);
                if(opacMail !=null){
                    emailService.sendSimpleMail(opacMail, Texts.getString("OPAC.EMAIL.MESSAGE.SUBJECT"),
                            MessageFormat.format(Texts.getString("OPAC.EMAIL.MESSAGE.BODY"), message.getIdSender(),message.getContent()));
                }
            }
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

    private String getOpacMail(String library){
        LibraryConfiguration libraryConfiguration = this.libraryConfigurationRepository.getByLibraryName(library);
        if(libraryConfiguration != null) {
            return libraryConfiguration.getOpacAdminMail();
        }else{
            return null;
        }
    }

    @GetMapping("/librarian_email")
    public ResponseEntity<?> getLibrarianEmail(@RequestHeader("Library") String lib) {
        String opacMail = getOpacMail(lib);
        Librarian librarian = new Librarian();
        librarian.setEmail(opacMail);
        return new ResponseEntity<>(librarian, HttpStatus.OK);

    }

    @PostMapping("/librarian_email")
    public ResponseEntity<?> updateLibrarianEmail(@RequestHeader("Library") String lib, @RequestBody String librarianEmail) {
        if (DataValidator.validateEmail(librarianEmail) == DataErrors.EMAIL_FORMAT_INVALID)
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

        LibraryConfiguration libraryConfiguration = this.libraryConfigurationRepository.getByLibraryName(lib);
        if(libraryConfiguration != null){
            libraryConfiguration.setOpacAdminMail(librarianEmail);
            libraryConfigurationRepository.save(libraryConfiguration);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
