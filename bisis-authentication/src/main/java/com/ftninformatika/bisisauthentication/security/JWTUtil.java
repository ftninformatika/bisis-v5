package com.ftninformatika.bisisauthentication.security;

import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Configuration
@PropertySource("classpath:config.properties")
public class JWTUtil {

    @Value("${security.token.secret.key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractLibrary(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("library", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        BisisUserDetailsImpl bisisUserDetails = (BisisUserDetailsImpl)userDetails; 
        claims.put("clientType", bisisUserDetails.getClientType());
        claims.put("userID", bisisUserDetails.getID());
        claims.put("username", bisisUserDetails.getUsername());
        claims.put("library", bisisUserDetails.getLibrary());
        claims.put("token_create_date", LocalDateTime.now());
        return createToken(claims, userDetails.getUsername());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        BisisUserDetailsImpl bisisUserDetails = (BisisUserDetailsImpl)userDetails;
        claims.put("userID", bisisUserDetails.getID());
        claims.put("username", bisisUserDetails.getUsername());
        claims.put("library", bisisUserDetails.getLibrary());
        return Jwts.builder().setClaims(claims).setSubject(bisisUserDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Jws<Claims> parseToken(String token) {
        if (token != null) {
            try {
                return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | SignatureException | IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

}
