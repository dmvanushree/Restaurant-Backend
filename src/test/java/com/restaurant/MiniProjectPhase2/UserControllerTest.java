package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.controller.UserController;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listUsers_success() {
        when(service.listUsers()).thenReturn(List.of(new User(), new User()));

        var users = controller.all(); // should return List<User>
        assertEquals(2, users.size());
    }

    @Test
    void getUserById_success() {
        User u = new User();
        u.setId(5L);

        when(service.findById(5L)).thenReturn(u);

        var response = controller.one(5L); // should return ResponseEntity<User> or User
        assertEquals(5L, response.getBody().getId());
    }
}
