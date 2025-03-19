package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final UserRepository userRepository;

    @GetMapping("/user-exists/{email}")
    public ResponseEntity<?> checkUserExists(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", user.isPresent());
        if (user.isPresent()) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.get().getId());
            userInfo.put("email", user.get().getEmail());
            userInfo.put("fullName", user.get().getFullName());
            userInfo.put("passwordLength", user.get().getPassword().length());
            
            if (user.get().getRole() != null) {
                userInfo.put("role", user.get().getRole().getRoleName());
            } else {
                userInfo.put("role", "No role assigned");
            }
            
            response.put("user", userInfo);
        }
        return ResponseEntity.ok(response);
    }
}
