package com.rimmelasghar.boilerplate.springboot.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;

// rimmel asghar
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Login request payload")
public class LoginRequest {

	@NotEmpty(message = "{login_email_not_empty}")
	@Schema(description = "User's email address", example = "user@example.com", required = true)
	private String email;

	@NotEmpty(message = "{login_password_not_empty}")
	@Schema(description = "User's password", example = "password123", required = true)
	private String password;

}
