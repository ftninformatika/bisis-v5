package com.ftninformatika.bisis.auth.security.service;

import com.ftninformatika.bisis.auth.exception.model.UserNotFoundException;
import com.ftninformatika.bisis.auth.model.LibrarianUser;
import com.ftninformatika.bisis.auth.model.MemberAuthentication;
import com.ftninformatika.bisis.auth.model.UserAuthentication;
import com.ftninformatika.bisis.auth.security.constants.SecurityConstants;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


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
                LibrarianUser user = getUserFromToken(tokenData);
                if (user != null) {
                    return new UserAuthentication(user);
                }
            }
            if ("member".equals(tokenData.getBody().get("clientType").toString())) { //autentifikacija korisnika (membera)
                LibraryMember member = getMemberFromToken(tokenData);
//                if (tokenExpired(token, member)){  //pitamo da li je istekao token?
//                    System.out.println("Your token has expired!");
//                    return null;
//                }
//                if (member != null ) {
                    member.setLastActivity(new Date());
                    libraryMemberRepository.save(member);
                    return new MemberAuthentication(member);
                }
//            }
        }
        return null;
    }



    public Jws<Claims> parseToken(final String token) {
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

    private LibraryMember getMemberFromToken(final Jws<Claims> tokenData){
          try{
            return libraryMemberRepository.findById(tokenData.getBody().get("userID").toString()).get();
        }catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("Member "
                    + tokenData.getBody().get("username").toString() + " not found");
        }
    }

    private LibrarianUser getUserFromToken(final Jws<Claims> tokenData) {
        try {
            return (LibrarianUser) userDetailsService
                    .loadUserByUsername(tokenData.getBody().get("username").toString());
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("LibrarianUser "
                    + tokenData.getBody().get("username").toString() + " not found");
        }
    }

    private boolean tokenExpired(final String token, LibraryMember member){
        Date now = new Date();

        if (token.equals(member.getAuthToken())) {

           long ONE_MINUTE_IN_MILLIS=60000;//millisecs

            long t= member.getLastActivity().getTime();
            Date expirationDate=new Date(t + ( ONE_MINUTE_IN_MILLIS * 15));


            return expirationDate.before(now); //da li je poslednja aktivnost bila pre vise od 15 min?
        }
        else
            return true;

    }
}
