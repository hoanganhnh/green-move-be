package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.RoleDto;
import com.rimmelasghar.boilerplate.springboot.model.Role;
import com.rimmelasghar.boilerplate.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roles")
@Tag(name = "Role Management", description = "API for managing roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Create a new role
     * Endpoint: POST /roles
     */
    @Operation(summary = "Create a new role", description = "Creates a new role with the provided name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Role created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Role with this name already exists")
    })
    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleDto roleDto, BindingResult bindingResult) {
        // Validate input data
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        String roleName = roleDto.getRole_name();

        // Check if role name already exists
        if (roleService.roleNameExists(roleName)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Role with this name already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Create and save the new role
        Role role = new Role(roleName);
        Role savedRole = roleService.saveRole(role);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    /**
     * Get all roles
     * Endpoint: GET /roles
     */
    @Operation(summary = "Get all roles", description = "Retrieves a list of all available roles")
    @ApiResponse(responseCode = "200", description = "List of roles retrieved successfully")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Get role by ID
     * Endpoint: GET /roles/{role_id}
     */
    @Operation(summary = "Get role by ID", description = "Retrieves a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role found"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{role_id}")
    public ResponseEntity<?> getRoleById(@PathVariable("role_id") Long roleId) {
        Optional<Role> role = roleService.getRoleById(roleId);
        
        if (role.isPresent()) {
            return ResponseEntity.ok(role.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Role not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Update role
     * Endpoint: PUT /roles/{role_id}
     */
    @Operation(summary = "Update role", description = "Updates an existing role's name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "409", description = "Another role with this name already exists")
    })
    @PutMapping("/{role_id}")
    public ResponseEntity<?> updateRole(@PathVariable("role_id") Long roleId, 
                                      @Valid @RequestBody RoleDto roleDto, 
                                      BindingResult bindingResult) {
        // Validate input data
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        String roleName = roleDto.getRole_name();

        // Check if role exists
        Optional<Role> existingRole = roleService.getRoleById(roleId);
        if (existingRole.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Role not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Check if the new role name already exists for a different role
        Optional<Role> roleWithSameName = roleService.getAllRoles().stream()
                .filter(r -> r.getRoleName().equals(roleName) && !r.getId().equals(roleId))
                .findFirst();
                
        if (roleWithSameName.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Another role with this name already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        // Update the role
        Role roleToUpdate = new Role(roleName);
        roleToUpdate.setId(roleId);
        Optional<Role> updatedRole = roleService.updateRole(roleId, roleToUpdate);

        return ResponseEntity.ok(updatedRole.get());
    }

    /**
     * Delete role
     * Endpoint: DELETE /roles/{role_id}
     */
    @Operation(summary = "Delete role", description = "Deletes a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @DeleteMapping("/{role_id}")
    public ResponseEntity<?> deleteRole(@PathVariable("role_id") Long roleId) {
        // Check if role exists
        Optional<Role> existingRole = roleService.getRoleById(roleId);
        if (existingRole.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Role not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Delete the role
        roleService.deleteRole(roleId);
        
        return ResponseEntity.noContent().build();
    }
}
