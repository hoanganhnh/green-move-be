package com.rimmelasghar.boilerplate.springboot.configuration;

import com.rimmelasghar.boilerplate.springboot.security.jwt.JwtAuthenticationFilter;
import com.rimmelasghar.boilerplate.springboot.security.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	
	private final CorsFilter corsFilter;

	@Bean
	public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		//@formatter:off

		return http.csrf().disable()
				.addFilterBefore(corsFilter, ChannelProcessingFilter.class)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/register","/health","/login","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/actuator/**").permitAll()
				.antMatchers("/roles/**").permitAll() // Temporarily allow access to roles API for testing
				.antMatchers("/test/**").permitAll() // Temporarily allow access to test endpoints
				.antMatchers("/vehicles/**").permitAll() // Temporarily allow access to vehicles API for testing
				.antMatchers("/locations/**").permitAll() // Temporarily allow access to locations API for testing
				.antMatchers("/rentals/**").permitAll() // Temporarily allow access to rentals API for testing
				.antMatchers("/reviews/**").permitAll() // Temporarily allow access to reviews API for testing
				.antMatchers("/payments/**").permitAll() // Temporarily allow access to payments API for testing
				.anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().build();

		//@formatter:on
	}


}
