package com.restaurant.MiniProjectPhase2;

import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.enums.Role;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
class UserServiceTest {

    @Test
    void createUser_success() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

        UserServiceImpl service = new UserServiceImpl(repo, encoder);

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole(Role.WAITER);
        user.setPassword("plaintext");

        Mockito.when(encoder.encode(any())).thenReturn("hashed");
        Mockito.when(repo.save(any())).thenReturn(user);

        User created = service.createUser(user);

        assertThat(created).isNotNull();
        assertThat(created.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void listUsers_success() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        UserServiceImpl service = new UserServiceImpl(repo, encoder);

        Mockito.when(repo.findAll()).thenReturn(List.of(new User()));

        List<User> users = service.listUsers();

        assertThat(users).isNotEmpty();
    }
}