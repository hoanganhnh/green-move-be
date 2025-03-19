package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User saveUser(User user);

    Optional<User> updateUser(Long id, User user);

    void deleteUser(Long id);

    boolean existsByEmail(String email);


    
    boolean existsByPhoneNumber(String phoneNumber);
}
