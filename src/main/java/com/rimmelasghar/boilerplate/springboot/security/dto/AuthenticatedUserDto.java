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

	private String email;

	private String password;

	private Role role;

}
