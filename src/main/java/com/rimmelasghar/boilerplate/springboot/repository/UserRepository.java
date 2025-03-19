package com.rimmelasghar.boilerplate.springboot.repository;

import com.rimmelasghar.boilerplate.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// rimmel asghar
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


	
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);


	
	boolean existsByPhoneNumber(String phoneNumber);

}
