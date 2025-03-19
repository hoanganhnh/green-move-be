package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.security.dto.RegistrationRequest;
import com.rimmelasghar.boilerplate.springboot.security.dto.RegistrationResponse;
import com.rimmelasghar.boilerplate.springboot.security.service.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// rimmel asghar
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
@Tag(name = "Registration", description = "Registration API")
public class RegistrationController {

	private final AuthUserService userService;

	@Operation(summary = "Register a new user", description = "Creates a new user account with username, email, and password")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "User successfully registered",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationResponse.class))}),
		@ApiResponse(responseCode = "400", description = "Invalid input or email/username already exists")
	})
	@PostMapping
	public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {

		final RegistrationResponse registrationResponse = userService.registration(registrationRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
	}

}
