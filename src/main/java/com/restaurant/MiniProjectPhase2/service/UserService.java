package com.restaurant.MiniProjectPhase2.service;

import com.restaurant.MiniProjectPhase2.model.User;
import java.util.List;
import com.restaurant.MiniProjectPhase2.model.User;
import com.restaurant.MiniProjectPhase2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> findAll();

    User findById(Long id);

    List<User> listUsers();

    User createUser(User user);

     User updateUser(User user);
    User deleteUser(Long id);



}


