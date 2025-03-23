package com.rimmelasghar.boilerplate.springboot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdateDto {

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String full_name;

    @Email(message = "Email should be valid")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    private String phone_number;
    
    private Long role_id;

    // Constructors
    public UserUpdateDto() {
    }

    public UserUpdateDto(String full_name, String email, String phone_number, Long role_id) {
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.role_id = role_id;
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
    
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    
    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
}
