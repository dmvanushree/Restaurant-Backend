package com.restaurant.MiniProjectPhase2.controller;

import com.restaurant.MiniProjectPhase2.enums.Role;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- REGISTER ----------
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return "User already exists!";
        }

        Role userRole;
        try {
            userRole = Role.valueOf(request.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid role! Available roles: " + Arrays.toString(Role.values());
        }

        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setRole(userRole);
        newUser.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(newUser);

        return "User registered successfully!";
    }

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<String> roles = List.of(user.getRole().name());  // Use uppercase role
            String token = jwtUtil.generateToken(user.getEmail(), roles);

            return new LoginResponse(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // DTOs
    public static record RegisterRequest(String name, String email, String password, String role) {}
    public static record LoginRequest(String email, String password) {}
    public static record LoginResponse(String token) {}
}
