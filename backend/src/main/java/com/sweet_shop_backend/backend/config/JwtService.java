package com.sweet_shop_backend.backend.config;

import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
                .expiration(new Date(System.currentTimeMillis()+100*60*60))
                .signWith(getSecretKey())
                .compact();
    }
}
