package com.rimmelasghar.boilerplate.springboot.security.service;

import com.rimmelasghar.boilerplate.springboot.service.UserValidationService;
import com.rimmelasghar.boilerplate.springboot.service.RoleService;
import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.model.Role;
import com.rimmelasghar.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import com.rimmelasghar.boilerplate.springboot.security.dto.RegistrationRequest;
import com.rimmelasghar.boilerplate.springboot.security.dto.RegistrationResponse;
import com.rimmelasghar.boilerplate.springboot.security.mapper.UserMapper;
import com.rimmelasghar.boilerplate.springboot.utils.GeneralMessageAccessor;
import com.rimmelasghar.boilerplate.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserValidationService userValidationService;

	private final GeneralMessageAccessor generalMessageAccessor;
	
	private final RoleService roleService;

	@Override
	public User findByEmail(String email) {

		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		// Set default role to USER
		Optional<Role> userRole = roleService.getRoleByName("USER");
		if (userRole.isEmpty()) {
			// Create USER role if it doesn't exist
			Role newRole = new Role("USER");
			user.setRole(roleService.saveRole(newRole));
		} else {
			user.setRole(userRole.get());
		}

		userRepository.save(user);

		final String email = registrationRequest.getEmail();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, email);

		log.info("{} registered successfully!", email);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByEmail(String email) {

		final User user = findByEmail(email);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}
}
