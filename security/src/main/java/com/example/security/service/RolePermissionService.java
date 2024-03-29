package com.example.security.service;

import com.example.security.model.Permission;
import com.example.security.model.Role;
import com.example.security.model.RolePermission;
import com.example.security.repository.PermissionRepository;
import com.example.security.repository.RolePermissionRepository;
import com.example.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public RolePermission addRolePermission(RolePermission rolePermission) {
        Role role = roleRepository.findByName(rolePermission.getRole().getName());
        Permission permission = permissionRepository.findByName(rolePermission.getPermission().getName());

        if (role != null && permission != null) {
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermission.setId(generateRandomId());
            return rolePermissionRepository.save(rolePermission);
        } else {
            throw new IllegalArgumentException("Invalid Role or Permission Name");
        }
    }

    private Long generateRandomId() {
        Random random = new Random();
        return random.nextLong();
    }

    public void deleteRolePermission(Long id) {
        rolePermissionRepository.deleteById(id);
    }

    public List<RolePermission> getAllRolePermissions() {
        return rolePermissionRepository.findAll();
    }
}