package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.model.Role;
import com.rimmelasghar.boilerplate.springboot.repository.RoleRepository;
import com.rimmelasghar.boilerplate.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> updateRole(Long id, Role roleDetails) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        
        if (roleOptional.isPresent()) {
            Role existingRole = roleOptional.get();
            existingRole.setRoleName(roleDetails.getRoleName());
            return Optional.of(roleRepository.save(existingRole));
        }
        
        return Optional.empty();
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public boolean roleNameExists(String roleName) {
        return roleRepository.findByRoleName(roleName).isPresent();
    }
    
    @Override
    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
