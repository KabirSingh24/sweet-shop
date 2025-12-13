package com.sweet_shop_backend.backend.auth;

import com.sweet_shop_backend.backend.auth.model.Role;
import com.sweet_shop_backend.backend.auth.model.User;
import com.sweet_shop_backend.backend.auth.repository.UserRepository;
import com.sweet_shop_backend.backend.auth.service.AuthService;
import com.sweet_shop_backend.backend.common.dto.AuthRequest;
import com.sweet_shop_backend.backend.common.dto.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // rolls back after each test
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll(); // clean DB before each test
    }

    @Test
    public void testRegisterUser_Success() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        String response = authService.register(request);

        assertNotNull(response);
        User user = userRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertTrue(passwordEncoder.matches("password123", user.getPassword()));
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        User existing = new User();
        existing.setEmail("test@example.com");
        existing.setPassword(passwordEncoder.encode("abc123"));
        existing.setRole(Role.USER);
        userRepository.save(existing);

        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertTrue(exception.getMessage().contains("Email already exists"));
    }

    @Test
    public void testLoginUser_Success() {
        User user = new User();
        user.setEmail("login@example.com");
        user.setPassword(passwordEncoder.encode("mypassword"));
        user.setRole(Role.USER);
        userRepository.save(user);

        AuthRequest request = new AuthRequest();
        request.setEmail("login@example.com");
        request.setPassword("mypassword");

        AuthResponse response = authService.login(request);

        assertNotNull(response.getToken());
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        User user = new User();
        user.setEmail("login@example.com");
        user.setPassword(passwordEncoder.encode("mypassword"));
        user.setRole(Role.USER);
        userRepository.save(user);

        AuthRequest request = new AuthRequest();
        request.setEmail("login@example.com");
        request.setPassword("wrongpassword");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertTrue(exception.getMessage().contains("Invalid credentials"));
    }

    @Test
    public void testLoginUser_EmailNotFound() {
        AuthRequest request = new AuthRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("password");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }


}

