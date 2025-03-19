package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAllRoles();

    Optional<Role> getRoleById(Long id);

    Role saveRole(Role role);

    Optional<Role> updateRole(Long id, Role role);

    void deleteRole(Long id);

    boolean roleNameExists(String roleName);
    
    Optional<Role> getRoleByName(String roleName);
}
