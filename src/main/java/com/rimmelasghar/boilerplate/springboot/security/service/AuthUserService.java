package com.rimmelasghar.boilerplate.springboot.security.service;

import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import com.rimmelasghar.boilerplate.springboot.security.dto.RegistrationRequest;
import com.rimmelasghar.boilerplate.springboot.security.dto.RegistrationResponse;

// rimmel asghar
public interface AuthUserService {

	User findByEmail(String email);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByEmail(String email);

}
