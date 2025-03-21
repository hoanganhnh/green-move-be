package com.rimmelasghar.boilerplate.springboot.security.dto;

import com.rimmelasghar.boilerplate.springboot.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

// rimmel asghar
@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "Login response containing JWT token and user information")
public class LoginResponse {

	@Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String token;
	
	@Schema(description = "User profile information")
	private UserProfileDto user;
	
	// Constructor for backward compatibility
	public LoginResponse(String token) {
		this.token = token;
		this.user = null;
	}
}
