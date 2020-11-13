package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.converter.ConverterFacade;
import com.ftninformatika.bisis.auth.dto.UserDTO;
import com.ftninformatika.bisis.auth.service.UserService;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.Texts;
import com.ftninformatika.bisis.rest_service.config.YAMLConfig;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
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
@RequestMapping("/signup")
public class SignUpController {

    private final UserService service;
    private final ConverterFacade converterFacade;
    @Autowired EmailService emailService;
    @Autowired LibraryMemberService libraryMemberService;
    @Autowired YAMLConfig yamlConfig;
    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired
    public SignUpController(final UserService service,
                            final ConverterFacade converterFacade) {
        this.service = service;
        this.converterFacade = converterFacade;
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody final UserDTO dto) {
        return new ResponseEntity<>(service.create(converterFacade.convert(dto)), HttpStatus.OK);
    }

    @PostMapping(value = "/opac")
    public ResponseEntity<?> signForOpac(@RequestHeader("Library") String library, @RequestBody LibraryMember newMember) {

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
            LibraryConfiguration libConf = libraryConfigurationRepository.getByLibraryName(library);
            emailService.sendOpacWelcomeTemplate(newMember, libConf);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(createdMember, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/opac/resend-email")
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
}