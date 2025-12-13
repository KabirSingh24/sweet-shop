package com.sweet_shop_backend.backend.auth.service;

import com.sweet_shop_backend.backend.auth.model.Role;
import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import com.sweet_shop_backend.backend.common.dto.AuthRequest;
import com.sweet_shop_backend.backend.common.dto.AuthResponse;
import com.sweet_shop_backend.backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(AuthRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("User already exists");
        }

        User user=User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return "User Register SuccessFully";
    }
    public AuthResponse login(AuthRequest request){
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        String token=jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
