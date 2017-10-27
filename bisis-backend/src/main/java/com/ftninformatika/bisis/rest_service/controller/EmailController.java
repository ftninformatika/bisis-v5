package com.ftninformatika.bisis.rest_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Petar on 10/27/2017.
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired JavaMailSender sender;

    @RequestMapping( value = "/sendTest")
    public void sendTestEmail(){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("jiricekova31@gmail.com");
        message.setSubject("testtest");
        message.setText("text");
        try{
            sender.send(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
