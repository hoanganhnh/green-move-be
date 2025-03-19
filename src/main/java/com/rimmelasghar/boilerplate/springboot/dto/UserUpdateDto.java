package com.rimmelasghar.boilerplate.springboot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserUpdateDto {

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String full_name;

    @Email(message = "Email should be valid")
    private String email;

    // Constructors
    public UserUpdateDto() {
    }

    public UserUpdateDto(String full_name, String email) {
        this.full_name = full_name;
        this.email = email;
    }

    // Getters and Setters
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
