package com.ftninformatika.bisis.auth.security.service;

import com.ftninformatika.bisis.auth.exception.model.ServiceException;
import com.ftninformatika.bisis.auth.model.LibrarianUser;
import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JsonWebTokenService implements TokenService {

    private static int tokenExpirationTime = 30;

    @Value("security.token.secret.key")
    private String tokenKey;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JsonWebTokenService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired LibraryMemberRepository libraryMemberRepository;

    @Override
    public String getToken(final String username, final String password) {
        if (username == null || password == null) {
            return null;
        }
        final LibrarianUser user = (LibrarianUser) userDetailsService.loadUserByUsername(username);
        Map<String, Object> tokenData = new HashMap<>();
        if (password.equals(user.getPassword())) {
            tokenData.put("clientType", "librarian");
            tokenData.put("userID", user.getId());
            tokenData.put("username", user.getUsername());
            tokenData.put("token_create_date", LocalDateTime.now());
            /*Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, tokenExpirationTime);
            tokenData.put("token_expiration_date", calendar.getTime());*/
            JwtBuilder jwtBuilder = Jwts.builder();
            //jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);
            return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();

        } else {
            throw new ServiceException("Librarian Authentication error", this.getClass().getName());
        }
    }

    public String getMemberToken(final String username, final String password) {
        if (username == null || password == null) {
            return null;
        }
        final LibraryMember user = libraryMemberRepository.findByUsername(username); //email im je username

        if (user == null || !BCrypt.checkpw(password, user.getPassword()))
            return null;

        Map<String, Object> tokenData = new HashMap<>();
        if (BCrypt.checkpw(password, user.getPassword())) {
            tokenData.put("clientType", "member");
            tokenData.put("userID", user.get_id());
            tokenData.put("username", user.getUsername());
            Calendar calendar = Calendar.getInstance();
            tokenData.put("token_create_date", calendar);
            /*Calendar calendar2 = Calendar.getInstance();
            calendar2.add(Calendar.MINUTE, tokenExpirationTime);
            tokenData.put("token_expiration_date", calendar2.getTime());*/
            JwtBuilder jwtBuilder = Jwts.builder();
            //jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);
            String encriptedToken = jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();


            user.setAuthToken(encriptedToken);
            user.setLastActivity(new Date());
            libraryMemberRepository.save(user);
            return encriptedToken;

        } else {
            throw new ServiceException("Library Member Authentication error", this.getClass().getName());
        }
    }

    @Override
    public String getLibrary(String username, String password) {
        return null;
    }

    public static void setTokenExpirationTime(final int tokenExpirationTime) {
        JsonWebTokenService.tokenExpirationTime = tokenExpirationTime;
    }
}
