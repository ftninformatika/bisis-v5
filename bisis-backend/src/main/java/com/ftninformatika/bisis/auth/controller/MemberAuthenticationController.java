package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.dto.LoginDTO;
import com.ftninformatika.bisis.auth.dto.TokenDTO;
import com.ftninformatika.bisis.auth.security.service.TokenService;
import com.ftninformatika.bisis.rest_service.controller.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petar on 10/13/2017.
 */

@RestController
@RequestMapping("/memauth")
public class MemberAuthenticationController {

    private final TokenService tokenService;

    @Autowired
    public MemberAuthenticationController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired MemberController memberController;

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> authenticate(@RequestBody final LoginDTO dto) {
        Map<String, Object> retVal = new HashMap<>();
        final String token = tokenService.getToken(dto.getUsername(), dto.getPassword());
        if (token != null) {
            final TokenDTO response = new TokenDTO();
            response.setToken(token);
            retVal.put("token", response);
            retVal.put("member_info", memberController.getMemberByUsername(dto.getUsername()));

            return retVal;
        } else {
            return (Map<String, Object>) new HashMap<>().put("message", "Error authenticating memeber!");
        }
    }
}
