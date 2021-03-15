package com.chat.HeyYo.security;

import com.chat.HeyYo.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Date;

import static com.chat.HeyYo.security.SecurityConstants.EXPIRATION_TIME;
import static com.chat.HeyYo.security.SecurityConstants.SECRET;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((System.currentTimeMillis() + EXPIRATION_TIME)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {

        return Jwts.parserBuilder().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws SignatureException {
        try {

            Jwts.parserBuilder().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(authToken);

            return true;
        }
        catch (MalformedJwtException e) {

            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e) {

            logger.error("JWT token is expired: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e) {

            logger.error("JWT token is unsupported: {}", e.getMessage());
        }
        catch (IllegalArgumentException e) {

            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
