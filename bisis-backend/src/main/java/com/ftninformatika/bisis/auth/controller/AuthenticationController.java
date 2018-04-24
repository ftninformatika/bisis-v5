package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.dto.LoginDTO;
import com.ftninformatika.bisis.auth.dto.TokenDTO;
import com.ftninformatika.bisis.auth.security.service.TokenService;
import com.ftninformatika.bisis.rest_service.controller.MemberController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private Logger log = Logger.getLogger(MemberController.class);

    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody final LoginDTO dto) {
        try {
            final String token = tokenService.getToken(dto.getUsername(), dto.getPassword());
            final TokenDTO response = new TokenDTO();
            response.setToken(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (NullPointerException e){
            log.warn("Neuspesno logovanje za korisnika: " + dto.getUsername() != null ? dto.getUsername() : "null");
            return new ResponseEntity<>("Authentication failed", HttpStatus.BAD_REQUEST);
        }
    }
}
