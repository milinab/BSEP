package com.example.security.controller;

import com.example.security.model.Skill;
import com.example.security.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/skill")
@AllArgsConstructor
public class SkillController {
    private SkillService skillService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSkill(@PathVariable Long id, @RequestBody Skill skillToUpdate) throws Exception {
        Skill updatedSkill = skillService.updateSkill(id, skillToUpdate);
        if (updatedSkill != null) {
            return ResponseEntity.ok("Skill updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
