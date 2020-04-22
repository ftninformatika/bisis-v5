package com.ftninformatika.bisis.rest_service.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author badf00d21  24.7.19.
 */
@Service
public class EmailService {


    @Autowired JavaMailSender javaMailSender;

    public void sendSimpleMail(String addressTo, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(addressTo);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

}
