package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.security.dto.LoginRequest;
import com.rimmelasghar.boilerplate.springboot.security.dto.LoginResponse;
import com.rimmelasghar.boilerplate.springboot.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "Authentication", description = "API for user authentication")
public class LoginController {

	private final JwtTokenService jwtTokenService;

	@Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully authenticated", 
			content = @Content(schema = @Schema(implementation = LoginResponse.class))),
		@ApiResponse(responseCode = "401", description = "Authentication failed")
	})
	@PostMapping
	public ResponseEntity<LoginResponse> loginRequest(@Valid @RequestBody LoginRequest loginRequest) {

		final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);

		return ResponseEntity.ok(loginResponse);
	}

}
