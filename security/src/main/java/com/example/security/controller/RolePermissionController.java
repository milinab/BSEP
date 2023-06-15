package com.example.security.controller;

import com.example.security.model.RolePermission;
import com.example.security.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/api/role-permissions")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;
    Logger logger = LoggerFactory.getLogger(RolePermissionController.class);

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RolePermission> addRolePermission(@RequestBody RolePermission rolePermission) {
        logger.info("Adding new role permission");
        RolePermission createdRolePermission = rolePermissionService.addRolePermission(rolePermission);
        logger.info("New role permission added successfully");
        return ResponseEntity.ok(createdRolePermission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable Long id) {
        logger.info("Deleting role permission with ID {}", id);
        rolePermissionService.deleteRolePermission(id);
        logger.info("Role permission deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RolePermission>> getAllRolePermissions() {
        logger.info("Getting all role permissions");
        List<RolePermission> rolePermissions = rolePermissionService.getAllRolePermissions();
        logger.info("Retrieved {} role permissions", rolePermissions.size());
        return ResponseEntity.ok(rolePermissions);
    }
}