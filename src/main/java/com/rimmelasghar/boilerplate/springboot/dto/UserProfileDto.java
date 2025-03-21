package com.rimmelasghar.boilerplate.springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimmelasghar.boilerplate.springboot.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User profile information")
public class UserProfileDto {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User's full name", example = "John Doe")
    @JsonProperty("full_name")
    private String fullName;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User's phone number", example = "+1234567890")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema(description = "User's role")
    private String role;
}
