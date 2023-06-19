package com.example.security.service;

import com.example.security.model.Skill;
import com.example.security.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final Logger logger = LoggerFactory.getLogger(SkillService.class);

    public Skill updateSkill(Long skillId, Skill updatedSkill) throws Exception {
        Optional<Skill> existingSkillOptional = skillRepository.findById(skillId);
        if (existingSkillOptional.isEmpty()) {
            logger.warn("Failed to update skill with ID: {}, reason: skill not found.", skillId);
            throw new Exception("Skill not found");
        }

        Skill existingSkill = existingSkillOptional.get();

        // Update the necessary fields of the existing skill with the new data
        existingSkill.setSkillName(updatedSkill.getSkillName());
        existingSkill.setSkillScore(updatedSkill.getSkillScore());

        // Save the updated skill in the repository
        logger.info("Skill with ID: {} successfully updated.", skillId);
        return skillRepository.save(existingSkill);
    }
}
