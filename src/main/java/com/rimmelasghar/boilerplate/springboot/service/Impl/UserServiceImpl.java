package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.repository.UserRepository;
import com.rimmelasghar.boilerplate.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        // Hash the password before saving
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            
            // Update only non-null fields
            if (userDetails.getFullName() != null) {
                existingUser.setFullName(userDetails.getFullName());
            }
            
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            
            if (userDetails.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            }
            

            
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }
            
            return Optional.of(userRepository.save(existingUser));
        }
        
        return Optional.empty();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}
