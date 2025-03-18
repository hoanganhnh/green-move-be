package com.rimmelasghar.boilerplate.springboot.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// rimmel asghar
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for user registration")
public class RegistrationResponse {

	@Schema(description = "Success message after registration", example = "User {username} registered successfully!")
	private String message;

}
