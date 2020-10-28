package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.Texts;
import com.ftninformatika.bisis.rest_service.config.YAMLConfig;
import com.ftninformatika.bisis.rest_service.repository.mongo.Librarian2Repository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.EmailService;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.utils.validators.memberdata.DataErrors;
import com.ftninformatika.utils.validators.memberdata.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping()
public class SignUpController {

    @Autowired EmailService emailService;
    @Autowired LibraryMemberService libraryMemberService;
    @Autowired YAMLConfig yamlConfig;
    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired Librarian2Repository librarianRepository;

    @PostMapping(value = "/signup/opac")
    public ResponseEntity<?> signForOpac(@RequestBody LibraryMember newMember) {
        if (DataValidator.validateEmail(newMember.getUsername()) == DataErrors.EMAIL_FORMAT_INVALID)
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

        LibraryMember existingUser = libraryMemberRepository.findByUsername(newMember.getUsername());

        if (existingUser != null && existingUser.getProfileActivated())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        else if (existingUser != null && !existingUser.getProfileActivated())
            libraryMemberRepository.delete(existingUser);

        // Creating account on new email for user who already had account
        if (newMember.getIndex() != null) {
            LibraryMember oldAccount = libraryMemberRepository.findByIndex(newMember.getIndex());
            if (oldAccount != null)
                libraryMemberRepository.delete(oldAccount);
        }

        // ADMIN - Creating account on new email for user who already had account
        if (newMember.getLibrarianIndex() != null) {
            LibraryMember oldAccount = libraryMemberRepository.findByLibrarianIndex(newMember.getLibrarianIndex());
            libraryMemberRepository.delete(oldAccount);
        }

        String activationToken = libraryMemberService.generateActivationToken(newMember);
        newMember.setActivationToken(activationToken);

        // Email doesn't exits in any user, or profile is not activated yet
        // save profile and send activation link
        LibraryMember createdMember = libraryMemberRepository.save(newMember);

        try {
            emailService.sendSimpleMail(createdMember.getUsername(), Texts.getString("EMAIL_ACITVATE_PROFILE_HEADING"),
                    MessageFormat.format(Texts.getString("EMAIL_ACTIVATE_PROFILE_BODY.0"), yamlConfig.getOpacOrigin()) + createdMember.getActivationToken());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(createdMember, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/signup/opac/resend-email")
    public ResponseEntity<?> resendEmail(@RequestBody String username)  {
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(username);
        if (libraryMember == null || libraryMember.getActivationToken() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            emailService.sendSimpleMail(libraryMember.getUsername(), Texts.getString("EMAIL_ACITVATE_PROFILE_HEADING"),
                    MessageFormat.format(Texts.getString("EMAIL_ACTIVATE_PROFILE_BODY.0"), yamlConfig.getOpacOrigin()) + libraryMember.getActivationToken());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/memauth/activate-account")
    public ResponseEntity<?> activateOpacAccount(@RequestBody String acitvateToken) {
        if (acitvateToken == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LibraryMember libraryMember = libraryMemberRepository.findByActivationToken(acitvateToken);
        if (libraryMember == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // If admin account
        if (libraryMember.getAuthorities().contains(Authority.ROLE_ADMIN.getAuthority())) {
            LibrarianDB librarian = librarianRepository.findById(libraryMember.getLibrarianIndex()).get();
            librarian.setRole(Librarian.Role.OPACADMIN);
            librarianRepository.save(librarian);
        }
        libraryMember.setActivationToken(null);
        libraryMember.setProfileActivated(true);
        libraryMemberRepository.save(libraryMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
