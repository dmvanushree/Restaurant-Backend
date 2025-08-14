package com.restaurant.MiniProjectPhase2.controller;

import com.restaurant.MiniProjectPhase2.dto.UserCreateRequest;
import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final PasswordEncoder encoder;

    public UserController(UserService svc, PasswordEncoder encoder) {
        this.userService = svc;
        this.encoder = encoder;
    }

    // Matches test method createUser_success()
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserCreateRequest req) {
        log.info("Creating user {}", req.email());
        var u = new User();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setRole(req.role());
        u.setPassword(encoder.encode(req.password()));

        var saved = userService.createUser(u); // service method from your test

        return ResponseEntity
                .created(URI.create("/users/" + saved.getId()))
                .body(saved);
    }

    // Matches test method listUsers_success()
    @GetMapping
    public List<User> all() {
        return userService.listUsers();
    }

    // Matches test method getUserById_success()
    @GetMapping("/{id}")
    public ResponseEntity<User> one(@PathVariable Long id) {
        var user = userService.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserCreateRequest req) {
        log.info("Updating user with id {}", id);

        // Fetch the existing user
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        // Update fields
        existingUser.setName(req.name());
        existingUser.setEmail(req.email());
        existingUser.setRole(req.role());

        // Encode password if provided
        if (req.password() != null && !req.password().isEmpty()) {
            existingUser.setPassword(encoder.encode(req.password()));
        }

        // Save updated user via service
        User updatedUser = userService.updateUser(existingUser);

        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userService.deleteUser(id); // You need to implement this in UserService and UserServiceImpl
        return ResponseEntity.ok("User deleted.");
    }


}
