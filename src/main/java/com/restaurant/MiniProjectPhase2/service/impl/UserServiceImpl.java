package com.restaurant.MiniProjectPhase2.service.impl;

import com.restaurant.MiniProjectPhase2.exception.ResourceNotFoundException;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import com.restaurant.MiniProjectPhase2.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public User create(User user) {
        return repo.save(user);
    }

    @Override
    public User createUser(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> listUsers() {
        return repo.findAll();
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null || !repo.existsById(user.getId())) {
            throw new ResourceNotFoundException("Cannot update. User not found with id: " + user.getId());
        }
        return repo.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        repo.delete(user);
        return user;
    }

}