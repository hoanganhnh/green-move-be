package com.rimmelasghar.boilerplate.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Role operations")
public class RoleDto {

    @NotBlank(message = "Role name is required")
    @Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters")
    @Schema(description = "Name of the role", example = "ADMIN", required = true)
    private String role_name;

    // Constructors
    public RoleDto() {
    }

    public RoleDto(String role_name) {
        this.role_name = role_name;
    }

    // Getters and Setters
    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
