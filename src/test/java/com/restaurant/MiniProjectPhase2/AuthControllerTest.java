package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.controller.AuthController;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil; // use real instance

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize JwtUtil with dummy values for testing
        jwtUtil = new JwtUtil("12345678901234567890123456789012", 3600000);
        authController = new AuthController(authManager, jwtUtil, userRepo, passwordEncoder);
    }

    @Test
    void register_success() {
        AuthController.RegisterRequest req = new AuthController.RegisterRequest("John", "john@example.com", "pass", "ADMIN");
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

        String response = authController.register(req);

        assertEquals("User registered successfully!", response);
        verify(userRepo).save(any(User.class));
    }

    @Test
    void register_existingUser() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(new User()));
        String response = authController.register(new AuthController.RegisterRequest("John", "john@example.com", "pass", "ADMIN"));
        assertEquals("User already exists!", response);
    }

    @Test
    void login_failure() {
        AuthController.LoginRequest req = new AuthController.LoginRequest("john@example.com", "wrong");
        doThrow(BadCredentialsException.class).when(authManager).authenticate(any());
        assertThrows(RuntimeException.class, () -> authController.login(req));
    }
}
