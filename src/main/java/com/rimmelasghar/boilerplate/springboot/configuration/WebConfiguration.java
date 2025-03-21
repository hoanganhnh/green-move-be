package com.rimmelasghar.boilerplate.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Instead of using wildcard (*), we'll set allowed origins dynamically
        // This is necessary because when allowCredentials is true, allowedOrigins cannot contain "*"
        config.addAllowedOriginPattern("*");
        
        // Allow credentials
        config.setAllowCredentials(true);
        
        // Allow common HTTP methods
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        
        // Allow all headers with wildcard
        config.addAllowedHeader("*");
        
        // Expose headers that frontend might need
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Credentials");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
