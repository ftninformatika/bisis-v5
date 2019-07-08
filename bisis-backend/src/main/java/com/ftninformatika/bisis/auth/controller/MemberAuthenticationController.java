package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.dto.LoginDTO;
import com.ftninformatika.bisis.auth.dto.TokenDTO;
import com.ftninformatika.bisis.auth.security.service.TokenService;
import com.ftninformatika.bisis.auth.service.LibraryMemberService;
import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petar on 10/13/2017.
 */
@RestController
@RequestMapping("/memauth")
public class MemberAuthenticationController {

    @Value("security.token.secret.key")
    private String secretKey;

    private final TokenService tokenService;
    @Autowired LibraryMemberService libraryMemberService;

    @Autowired
    public MemberAuthenticationController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired LibraryMemberRepository libraryMemberRepository;

    @PostMapping
    public Map<String, Object> authenticate(@RequestBody final LoginDTO dto) {
        Map<String, Object> retVal = new HashMap<>();
        final String token = tokenService.getMemberToken(dto.getUsername(), dto.getPassword());
        if (token != null) {
            final TokenDTO response = new TokenDTO();
            response.setToken(token);
            retVal.put("token", response);
            LibraryMember m = libraryMemberRepository.findByUsername(dto.getUsername());
            m.setPassword(null);
            m.setPasswordResetString(null);
            retVal.put("member_info", m);
            return retVal;
        } else {
            return (Map<String, Object>) new HashMap<>().put("message", "Error authenticating memeber!");
        }
    }

    /**
     *
     * @param acitvateToken
     * @return
     */
    @PostMapping(value = "/activate-account")
    public ResponseEntity<?> activateOpacAccount(@RequestBody String acitvateToken) {
        if (acitvateToken == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // TODO- check if token expired
        LibraryMember libraryMember = libraryMemberRepository.findByActivationToken(acitvateToken);
        if (libraryMember == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        libraryMember.setActivationToken(null);
        libraryMember.setProfileActivated(true);
        libraryMemberRepository.save(libraryMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
