package com.ftninformatika.bisis.rest_service.controller;

import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

/**
 * Created by Petar on 10/27/2017.
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @RequestMapping( value = "/sendTest")
    public void sendEmailWithoutTemplating() throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("cicero@mala-tempora.currunt", "Marco Tullio Cicerone "))
                .to(Lists.newArrayList(new InternetAddress("jiricekova31@gmail.com", "Pomponius Attĭcus")))
                .subject("Laelius de amicitia")
                .body("Firmamentum autem stabilitatis constantiaeque eius, quam in amicitia quaerimus, fides est.")
                .encoding("UTF-8").build();

        emailService.send(email);
    }

}
