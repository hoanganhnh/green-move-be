package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.UserDto;
import com.rimmelasghar.boilerplate.springboot.dto.UserUpdateDto;
import com.rimmelasghar.boilerplate.springboot.model.Role;
import com.rimmelasghar.boilerplate.springboot.model.User;

import com.rimmelasghar.boilerplate.springboot.service.RoleService;
import com.rimmelasghar.boilerplate.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "API for managing users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Create a new user
     * Endpoint: POST /users
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        // Validate input data
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // Check if email already exists
        if (userService.existsByEmail(userDto.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Check if phone number already exists
        if (userDto.getPhone_number() != null && userService.existsByPhoneNumber(userDto.getPhone_number())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Phone number already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Check if role exists
        Optional<Role> roleOptional = Optional.empty();
        if (userDto.getRole_id() != null) {
            roleOptional = roleService.getRoleById(userDto.getRole_id());
            if (roleOptional.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Role not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        }

        // Create user from DTO
        User user = new User();
        user.setFullName(userDto.getFull_name());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhone_number());
        

        
        // Set role if provided
        if (roleOptional.isPresent()) {
            user.setRole(roleOptional.get());
            
        } else {
            // Default to USER role if no role is provided
            Optional<Role> defaultRoleOptional = roleService.getRoleByName("USER");
            if (defaultRoleOptional.isPresent()) {
                user.setRole(defaultRoleOptional.get());
            }
        }

        // Save user
        User savedUser = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Get all users
     * Endpoint: GET /users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID
     * Endpoint: GET /users/{user_id}
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable("user_id") Long userId) {
        Optional<User> user = userService.getUserById(userId);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Update user
     * Endpoint: PUT /users/{user_id}
     */
    @PutMapping("/{user_id}")
    public ResponseEntity<?> updateUser(@PathVariable("user_id") Long userId, 
                                      @Valid @RequestBody UserUpdateDto userUpdateDto, 
                                      BindingResult bindingResult) {
        // Validate input data
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // Check if user exists
        Optional<User> existingUserOpt = userService.getUserById(userId);
        if (existingUserOpt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        User existingUser = existingUserOpt.get();

        // Check if email is being updated and already exists
        if (userUpdateDto.getEmail() != null && 
            !userUpdateDto.getEmail().equals(existingUser.getEmail()) && 
            userService.existsByEmail(userUpdateDto.getEmail())) {
            
            Map<String, String> error = new HashMap<>();
            error.put("error", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Update user fields
        if (userUpdateDto.getFull_name() != null) {
            existingUser.setFullName(userUpdateDto.getFull_name());
        }
        
        if (userUpdateDto.getEmail() != null) {
            existingUser.setEmail(userUpdateDto.getEmail());
        }

        // Save updated user
        User updatedUser = userService.saveUser(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user
     * Endpoint: DELETE /users/{user_id}
     */
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        // Check if user exists
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Delete the user
        userService.deleteUser(userId);
        
        return ResponseEntity.noContent().build();
    }
}
