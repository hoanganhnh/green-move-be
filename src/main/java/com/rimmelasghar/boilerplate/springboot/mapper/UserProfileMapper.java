package com.rimmelasghar.boilerplate.springboot.mapper;

import com.rimmelasghar.boilerplate.springboot.dto.UserProfileDto;
import com.rimmelasghar.boilerplate.springboot.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    /**
     * Convert User entity to UserProfileDto
     * 
     * @param user the user entity
     * @return UserProfileDto with user information
     */
    public static UserProfileDto toUserProfileDto(User user) {
        if (user == null) {
            return null;
        }
        
        return UserProfileDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole() != null ? user.getRole().getRoleName() : null)
                .build();
    }
}
