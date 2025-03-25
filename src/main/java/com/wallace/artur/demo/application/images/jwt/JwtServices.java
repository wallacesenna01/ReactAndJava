package com.wallace.artur.demo.application.images.jwt;

import com.wallace.artur.demo.domain.AccessToken;
import com.wallace.artur.demo.domain.entity.User;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServices {

    private final SecretKeyGenerator generator;

    public AccessToken genereteToken(User user) {

        var key = generator.getKey();
        var expirationDate = genereteExpirationDate();
        var claims = generateTokenClaims(user);

        String token =  Jwts.builder()
                .signWith(key)
                .subject(user.getEmail())
                        .expiration(expirationDate)
                            .claims(claims)
                                .compact();

        return new AccessToken(token);
    }

    private Date genereteExpirationDate() {
        var expirationMinuts = 60;
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinuts);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Object> generateTokenClaims(User user ) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        return claims;
    }
}
