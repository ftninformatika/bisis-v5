package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.dto.ChangePasswordDTO;
import com.ftninformatika.bisis.opac2.dto.ShelfDto;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.Texts;
import com.ftninformatika.bisis.rest_service.config.YAMLConfig;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.EmailService;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.bisisauthentication.security.JWTUtil;
import com.ftninformatika.utils.validators.security.PasswordCodes;
import com.ftninformatika.utils.validators.security.PasswordValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Petar on 10/18/2017.
 */

@RestController
@RequestMapping("/library_members")
public class LibraryMemberController {

    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired
    JWTUtil jwtUtil;
//    @Autowired JsonWebTokenAuthenticationService jsonWebTokenAuthenticationService;
    @Autowired LibraryMemberService libraryMemberService;
    @Autowired EmailService emailService;
    @Autowired YAMLConfig yamlConfig;

    @PostMapping("/prolong_lending")
    public ResponseEntity prolongLending(@RequestHeader("Authorization") String authToken,
                                         @RequestBody String lendingId) {
        if (libraryMemberService.prolongLending(authToken, lendingId)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }
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
    public ResponseEntity<LibraryMember> getMemberByActivationToken(@RequestBody String activationToken) {
        if (activationToken == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Jws<Claims> claimsJws = jwtUtil.parseToken(activationToken);
        if (claimsJws != null && claimsJws.getBody().getExpiration().before(new Date()))
            return new ResponseEntity<>(HttpStatus.GONE);
        LibraryMember libraryMember = libraryMemberRepository.findByActivationToken(activationToken);
        if (libraryMember == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(libraryMember, HttpStatus.OK);
    }

    @PostMapping("/change_password")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO == null || changePasswordDTO.getNewPassword() == null
                || changePasswordDTO.getUsername() == null)
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(changePasswordDTO.getUsername());
        if (libraryMember == null)
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        if (PasswordValidator.validatePasswordStrength(changePasswordDTO.getNewPassword()) == PasswordCodes.NOT_STRONG_ENOUGH)
            return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
        String passHash = BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt());
        libraryMember.setPassword(passHash);
        libraryMember = libraryMemberRepository.save(libraryMember);
        if (libraryMember == null)
            return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody String username) {
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(username);
        if (libraryMember == null)
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        String activationToken = libraryMemberService.generateActivationToken(libraryMember);
        libraryMember.setActivationToken(activationToken);
        libraryMemberRepository.save(libraryMember);
        try {
            emailService.sendSimpleMail(libraryMember.getUsername(), Texts.getString("PASSWORD_RESTART_HEADING"),
                    MessageFormat.format(Texts.getString("PASSWORD_RESTART_BODY.0"), yamlConfig.getOpacOrigin()) + libraryMember.getActivationToken());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }

    @PostMapping("/add_to_shelf")
    public ResponseEntity<Boolean> addToShelf(@RequestBody ShelfDto shelfDto) {
        if (!libraryMemberService.addToShelf(shelfDto))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/remove_from_shelf")
    public ResponseEntity<Boolean> removeFromShel(@RequestBody ShelfDto shelfDto) {
        if (!libraryMemberService.removeFromShelf(shelfDto))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/get_shelf")
    public ResponseEntity<List<Book>> getShelf(@RequestHeader("Library") String lib, @RequestBody String username) {
        List<Book> retVal = libraryMemberService.getShelf(username, lib);
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
