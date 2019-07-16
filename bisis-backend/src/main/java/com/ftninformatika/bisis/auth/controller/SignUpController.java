package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.converter.ConverterFacade;
import com.ftninformatika.bisis.auth.dto.UserDTO;
import com.ftninformatika.bisis.auth.service.UserService;
import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import com.ftninformatika.utils.validators.memberdata.DataErrors;
import com.ftninformatika.utils.validators.memberdata.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private final UserService service;
    private final ConverterFacade converterFacade;
    private JavaMailSender mailSender;
    @Autowired LibraryMemberService libraryMemberService;
    @Autowired LibraryMemberRepository libraryMemberRepository;

    @Autowired
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

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
    public ResponseEntity<?> signForOpac(@RequestBody LibraryMember newMember) {
        if (DataValidator.validateEmail(newMember.getUsername()) == DataErrors.EMAIL_FORMAT_INVALID)
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

        LibraryMember existingUser = libraryMemberRepository.findByUsername(newMember.getUsername());

        if (existingUser != null && existingUser.getProfileActivated())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        else if (existingUser != null && !existingUser.getProfileActivated())
            libraryMemberRepository.delete(existingUser);

        String activationToken = libraryMemberService.generateActivationToken(newMember);
        newMember.setActivationToken(activationToken);

        // Email doesn't exits in any user, or profile is not activated yet
        // save profile and send activation link
        LibraryMember createdMember = libraryMemberRepository.save(newMember);

        // TODO- send e-mail for activation
        //
        return new ResponseEntity<>(createdMember, HttpStatus.ACCEPTED);
    }
}
