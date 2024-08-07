package com.startup.oda.security.jwt;


import com.startup.oda.entity.enums.RoleEnum;
import com.startup.oda.exception.exceptionsList.ActiveTokenRevokedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${jwt.key}")
    private String jwtSecret;
    @Value("${brevo.mail.secret}")
    private String mailSecret;

    public String generateJwtToken(String username, RoleEnum role) {
        Instant now = Instant.now();
        String jwtExpirationMs = "3600000";
        Instant expirationTime = now.plusMillis(Long.parseLong(jwtExpirationMs));
        Date expirationDate = Date.from(expirationTime);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        if (TokenManager.revokedTokens.contains(authToken)) throw new ActiveTokenRevokedException();
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    public String generateMailToken(String email){
        String jwtExpirationMs = "600000";
        Instant expirationTime = Instant.now().plusMillis(Long.parseLong(jwtExpirationMs));
        Date expirationDate = Date.from(expirationTime);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(mailKey())
                .compact();
    }
    public boolean validateMailToken(String mailToken){
        try {
            Jwts.parserBuilder().setSigningKey(mailKey()).build().parse(mailToken);
            logger.info("Email verified successfully");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Mail token is expired: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Invalid Mail signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid Mail token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Mail token is unsupported: {}", e.getMessage());
        }
        return false;
    }
    public String getUsernameFromMailToken(String token) {
        return Jwts.parserBuilder().setSigningKey(mailKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    private Key mailKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(mailSecret));
    }
}
