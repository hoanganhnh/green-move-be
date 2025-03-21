package com.rimmelasghar.boilerplate.springboot.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

// rimmel asghar
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "Request object for user registration")
public class RegistrationRequest {

	@Schema(description = "User's email address", example = "user@example.com", required = true)
	@Email(message = "{registration_email_is_not_valid}")
	@NotEmpty(message = "{registration_email_not_empty}")
	private String email;

	@Schema(description = "User's full name", example = "John Doe", required = true)
	@NotEmpty(message = "{registration_fullname_not_empty}")
	private String fullName;

	@Schema(description = "User's password", example = "password123", required = true)
	@NotEmpty(message = "{registration_password_not_empty}")
	private String password;
	
	@Schema(description = "User's phone number", example = "+1234567890")
	@Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
	private String phoneNumber;

}
