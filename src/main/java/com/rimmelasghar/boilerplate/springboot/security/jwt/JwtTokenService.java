package com.rimmelasghar.boilerplate.springboot.security.jwt;

import com.rimmelasghar.boilerplate.springboot.dto.UserProfileDto;
import com.rimmelasghar.boilerplate.springboot.mapper.UserProfileMapper;
import com.rimmelasghar.boilerplate.springboot.security.mapper.UserMapper;
import com.rimmelasghar.boilerplate.springboot.security.service.AuthUserService;
import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import com.rimmelasghar.boilerplate.springboot.security.dto.LoginRequest;
import com.rimmelasghar.boilerplate.springboot.security.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

	private final AuthUserService userService;

	private final JwtTokenManager jwtTokenManager;

	private final AuthenticationManager authenticationManager;

	public LoginResponse getLoginResponse(LoginRequest loginRequest) {

		final String email = loginRequest.getEmail();
		final String password = loginRequest.getPassword();

		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		// Get the user directly from the database
		final User user = userService.findByEmail(email);
		final String token = jwtTokenManager.generateToken(user);

		// Convert user to UserProfileDto
		final UserProfileDto userProfileDto = UserProfileMapper.toUserProfileDto(user);

		log.info("{} has successfully logged in!", user.getEmail());

		return LoginResponse.builder()
			.token(token)
			.user(userProfileDto)
			.build();
	}

}
