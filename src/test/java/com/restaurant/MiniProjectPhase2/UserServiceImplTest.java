package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@test.com");
        user.setPassword("pass123");
    }

    @Test
    void create_success() {
        when(repo.save(user)).thenReturn(user);
        User result = service.create(user);
        assertEquals("Alice", result.getName());
    }

    @Test
    void createUser_encodesPassword() {
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");
        when(repo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = service.createUser(user);
        assertEquals("encodedPass", result.getPassword());
    }

    @Test
    void findById_found() {
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        User result = service.findById(1L);
        assertEquals("Alice", result.getName());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void findAll_success() {
        when(repo.findAll()).thenReturn(List.of(user));
        List<User> list = service.findAll();
        assertEquals(1, list.size());
    }

    @Test
    void listUsers_success() {
        when(repo.findAll()).thenReturn(List.of(user));
        List<User> list = service.listUsers();
        assertEquals(1, list.size());
    }
}
