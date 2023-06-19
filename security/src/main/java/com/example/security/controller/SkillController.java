package com.example.security.controller;

import com.example.security.model.Skill;
import com.example.security.service.SkillService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    //private final Logger logger = LoggerFactory.getLogger(SkillController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSkill(@PathVariable Long id, @RequestBody Skill skillToUpdate) throws Exception {
        //logger.info("Updating skill with ID {}", skillToUpdate.getId());
        Skill updatedSkill = skillService.updateSkill(id, skillToUpdate);
        if (updatedSkill != null) {
            //logger.info("Skill updated successfully");
            return ResponseEntity.ok("Skill updated successfully");
        } else {
            //logger.warn("Failed to update skill");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
