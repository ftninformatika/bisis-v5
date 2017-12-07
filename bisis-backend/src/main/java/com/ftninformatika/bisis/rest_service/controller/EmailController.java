package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired EmailService emailService;

    @Autowired MemberRepository memberRepository;

    @Autowired LibraryMemberRepository libraryMemberRepository;




    public void sendSimpleEmail(String fromName, String toAddress, String subject, String body) throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress(fromName, " "))
                .to(Lists.newArrayList(new InternetAddress(toAddress, "")))
                .subject(subject)
                .body(body)
                .encoding("UTF-8").build();

        emailService.send(email);

    }

}
