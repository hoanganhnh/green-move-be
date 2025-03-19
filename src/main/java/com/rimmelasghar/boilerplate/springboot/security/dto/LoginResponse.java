package com.rimmelasghar.boilerplate.springboot.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

// rimmel asghar
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Login response containing JWT token")
public class LoginResponse {

	@Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	private String token;

}
