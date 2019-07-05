package com.ftninformatika.bisis.auth.service;

import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.utils.date.DateUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author badf00d21  5.7.19.
 */
@Service
public class LibraryMemberService {


    @Value("security.token.secret.key")
    private String tokenKey;

    @Autowired LibraryMemberRepository libraryMemberRepository;

    /**
     * Checks if provided email already exist activated in
     * OPAC users collection (library_members)
     * @param email
     * @return
     */
    public boolean emailExistAndActivated(String email) {
        if (email == null) return false;
        LibraryMember lm = libraryMemberRepository.findByUsername(email);
        if (lm != null && lm.getProfileActivated()) return false;
        return true;
    }

    /**
     *
     * @param libraryMember- new OPAC account, without activation link
     * @return- activation token, valid for next 5 days
     */
    public String generateActivationToken(LibraryMember libraryMember) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("username", libraryMember.getUsername());
        Date acivationDeadline =  DateUtils.getNextDay(DateUtils.getNextDay(DateUtils.getNextDay(
                DateUtils.getNextDay(DateUtils.getNextDay(new Date()))
        )));
        tokenData.put("acivationDate", acivationDeadline);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData);
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
    }

}
