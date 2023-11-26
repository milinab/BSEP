package com.example.security.controller;

import com.example.security.model.RolePermission;
import com.example.security.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<RolePermission> addRolePermission(@RequestBody RolePermission rolePermission) {
        RolePermission createdRolePermission = rolePermissionService.addRolePermission(rolePermission);
        logger.info("New role permission added successfully");
        return ResponseEntity.ok(createdRolePermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable Long id) {
        rolePermissionService.deleteRolePermission(id);
        logger.info("Role permission with ID: {}, deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RolePermission>> getAllRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionService.getAllRolePermissions();
        return ResponseEntity.ok(rolePermissions);
    }
}