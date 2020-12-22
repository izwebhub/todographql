package com.izwebacademy.todographql.utils;

import com.izwebacademy.todographql.models.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //o

    public String generate(JwtUser jwtUser) {
        return Jwts.builder() // (1)
                .setSubject(jwtUser.getUsername())      // (2)
                .signWith(key)          // (3)
                .compact();

    }

    public JwtUser validate(String token) {
        JwtUser jwtUser;

        try {
            Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            jwtUser = new JwtUser();
            jwtUser.setUsername(body.getSubject());
        } catch (JwtException e) {
            //don't trust the JWT!
            throw new EntityException(e.getLocalizedMessage(), token);
        }

        return jwtUser;
    }
}
