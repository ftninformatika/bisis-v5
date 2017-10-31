package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.models.circ.LibraryMember;
import com.ftninformatika.bisis.models.circ.PasswordResetDTO;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by Petar on 10/18/2017.
 */

@RestController
@RequestMapping("/library_members")
public class LibraryMemberContrroller {

    @Autowired LibraryMemberRepository libraryMemberRepository;

    @Autowired EmailController emailController;

    @RequestMapping( value = "/generate_reset", method = RequestMethod.GET)
    public boolean generateReset(@RequestParam("email") String email) {
        LibraryMember lm = libraryMemberRepository.findByUsername(email); //by email zapravo
        if (lm == null) return false;
        lm.setPasswordResetString(randomStringGenerator());
        libraryMemberRepository.save(lm);
        String emailBody = "Молимо вас продужите на наведени линк како би рестартовали вашу лозинку http://localhost:4200/reset_password/" + lm.getPasswordResetString() +"\n";
        new Thread(() -> {
                try {
                    emailController.sendSimpleEmail("bisis_support", lm.getUsername(), "Рестарт лозинке", emailBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }).start();
        return true;


    }

    @RequestMapping( value = "/{passwordResetString}", method = RequestMethod.GET)
    public boolean getReset(@PathVariable String passwordResetString) {
        return libraryMemberRepository.findByPasswordResetString(passwordResetString) != null;
    }


    @RequestMapping( value = "/reset_password", method = RequestMethod.POST)
    public boolean resetPassword(@RequestBody PasswordResetDTO newPassword) {
        LibraryMember lm = libraryMemberRepository.findByPasswordResetString(newPassword.getPasswordResetString());
        if (lm != null && newPassword.getNewPassword() != null && (!"".equals(newPassword.getNewPassword())) &&
                newPassword.getNewPassword().length() > 5 ){ // pass mora biti duze od 6 char
            lm.setPassword(newPassword.getNewPassword());
            libraryMemberRepository.save(lm);
            return true;
        }
        return false;
    }

    private String randomStringGenerator(){
        char[] chars = "ABCDEFGIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789+-*=".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }
}
