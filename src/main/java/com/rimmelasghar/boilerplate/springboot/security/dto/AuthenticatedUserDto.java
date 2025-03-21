package com.rimmelasghar.boilerplate.springboot.security.dto;

import com.rimmelasghar.boilerplate.springboot.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// rimmel asghar
@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {

	private Long id;
	
	private String email;

	private String password;
	
	private String fullName;
	
	private String phoneNumber;

	private Role role;

}
