package com.sweet_shop_backend.backend.config;

import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtService {

    @Value("${jwt.secretkey}")
    private  String jwtSecretKey;

    private final UserRepository userRepository;

    private SecretKey getSecretKey(){return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));}


    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        try {
            Claims clamis= Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String email = clamis.getSubject();
            Date exp = clamis.getExpiration();

            return email.equals(user.getEmail()) && !exp.before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

}
