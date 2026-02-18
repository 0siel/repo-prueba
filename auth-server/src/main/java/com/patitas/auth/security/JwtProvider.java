package com.patitas.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret:ed8a3cb1315625aaba50a134b42e488e2ac0405b059eeadbe61a48f6a405bd8e}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private int expiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(Authentication authentication) {
        
        UserDetails usuarioPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .subject(usuarioPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getSecretKey())
                .compact();
    }
}
