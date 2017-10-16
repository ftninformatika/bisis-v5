package com.ftninformatika.bisis.auth.security.service;

import com.ftninformatika.bisis.auth.exception.model.UserNotFoundException;
import com.ftninformatika.bisis.auth.model.MemberAuthentication;
import com.ftninformatika.bisis.auth.model.User;
import com.ftninformatika.bisis.auth.model.UserAuthentication;
import com.ftninformatika.bisis.auth.security.constants.SecurityConstants;
import com.ftninformatika.bisis.models.circ.Member;
import com.ftninformatika.bisis.models.circ.pojo.LibraryMember;
import com.ftninformatika.bisis.rest_service.controller.MemberController;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class JsonWebTokenAuthenticationService implements TokenAuthenticationService {

    @Value("security.token.secret.key")
    private String secretKey;

    private final UserDetailsService userDetailsService;
    @Autowired private LibraryMemberRepository libraryMemberRepository;

    @Autowired
    public JsonWebTokenAuthenticationService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(final HttpServletRequest request) {
        final String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        final Jws<Claims> tokenData = parseToken(token);
        if (tokenData != null) {

            if ("librarian".equals(tokenData.getBody().get("clientType").toString())) { //autentifikacija bibliotekara
                User user = getUserFromToken(tokenData);
                if (user != null) {
                    return new UserAuthentication(user);
                }
            }
            if ("member".equals(tokenData.getBody().get("clientType").toString())) { //autentifikacija korisnika (membera)
                LibraryMember member = getMememberFromToken(tokenData);
                if (member != null )
                    return new MemberAuthentication(member);
            }
        }
        return null;
    }


    public Authentication authenticateMember(final HttpServletRequest request) {
        final String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        final Jws<Claims> tokenData = parseToken(token);
        if (tokenData != null) {
            LibraryMember user = getMememberFromToken(tokenData);
            if (user != null) {
                return new MemberAuthentication(user);
            }
        }
        return null;
    }

    private Jws<Claims> parseToken(final String token) {
        if (token != null) {
            try {
                return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | SignatureException | IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    private LibraryMember getMememberFromToken(final Jws<Claims> tokenData){
          try{
            return libraryMemberRepository.findOne(tokenData.getBody().get("userID").toString());
        }catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("Member "
                    + tokenData.getBody().get("username").toString() + " not found");
        }
    }

    private User getUserFromToken(final Jws<Claims> tokenData) {
        try {
            return (User) userDetailsService
                    .loadUserByUsername(tokenData.getBody().get("username").toString());
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("User "
                    + tokenData.getBody().get("username").toString() + " not found");
        }
    }
}
