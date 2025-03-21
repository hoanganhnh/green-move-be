package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.UserProfileDto;
import com.rimmelasghar.boilerplate.springboot.mapper.UserProfileMapper;
import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.security.service.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "User Profile", description = "API for managing user profile")
public class ProfileController {

    private final AuthUserService authUserService;

    @Operation(
        summary = "Get current user profile", 
        description = "Returns the profile information of the currently authenticated user",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved user profile", 
            content = @Content(schema = @Schema(implementation = UserProfileDto.class))
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - not authenticated")
    })
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUserProfile() {
        // Get the authenticated user's email from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        // Find the user by email
        User user = authUserService.findByEmail(email);
        
        // Convert to DTO
        UserProfileDto userProfileDto = UserProfileMapper.toUserProfileDto(user);
        
        return ResponseEntity.ok(userProfileDto);
    }
}
