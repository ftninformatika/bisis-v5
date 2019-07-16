package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.auth.security.service.JsonWebTokenAuthenticationService;
import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.circ.pojo.PasswordResetDTO;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.utils.validators.security.PasswordCodes;
import com.ftninformatika.utils.validators.security.PasswordValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Random;

/**
 * Created by Petar on 10/18/2017.
 */

@RestController
@RequestMapping("/library_members")
public class LibraryMemberController {

    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired JsonWebTokenAuthenticationService jsonWebTokenAuthenticationService;
    @Autowired LibraryMemberService libraryMemberService;

    @PostMapping("/activate_account")
    public ResponseEntity<Boolean> activateAccount(@RequestBody LibraryMember libraryMember) {
        if (libraryMember == null || libraryMember.getPassword() == null)
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        if (PasswordValidator.validatePasswordStrength(libraryMember.getPassword()) == PasswordCodes.STRONG_ENOUGH) {
            if (libraryMemberService.activateMember(libraryMember))
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/get_member_by_activation_token")
    public ResponseEntity<?> getMemberByActivationToken(@RequestBody String activationToken) {
        if (activationToken == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Jws<Claims> claimsJws = jsonWebTokenAuthenticationService.parseToken(activationToken);
        if (claimsJws != null && claimsJws.getBody().getExpiration().before(new Date()))
            return new ResponseEntity<>(HttpStatus.GONE);
        LibraryMember libraryMember = libraryMemberRepository.findByActivationToken(activationToken);
        if (libraryMember == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(libraryMember, HttpStatus.OK);
    }

    @RequestMapping( value = "/{passwordResetString}", method = RequestMethod.GET)
    public boolean getReset(@PathVariable String passwordResetString) {
        return libraryMemberRepository.findByPasswordResetString(passwordResetString) != null;
    }


    @RequestMapping( value = "/reset_password", method = RequestMethod.POST)
    public boolean resetPassword(@RequestBody PasswordResetDTO newPassword) {

        if (!newPassword.getNewPassword().equals(newPassword.getNewPasswordAgain())) //ako se ne poklapaju
            return false;

        LibraryMember lm = libraryMemberRepository.findByPasswordResetString(newPassword.getPasswordResetString());

        if (lm == null)
            return false;

        if (!lm.get_id().equals(newPassword.getUserId()))
            return false;

        if ( newPassword.getNewPassword() != null && (!"".equals(newPassword.getNewPassword())) &&
                newPassword.getNewPassword().length() > 5 ){ // pass mora biti duze od 6 char
            lm.setPassword(newPassword.getNewPassword());
            libraryMemberRepository.save(lm);
            return true;
        }
        return false;
    }

    private String randomStringGenerator(){
        char[] chars = "ABCDEFGIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789".toCharArray();
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
