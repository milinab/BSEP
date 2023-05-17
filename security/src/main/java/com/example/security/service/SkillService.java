package com.example.security.service;

import com.example.security.model.Skill;
import com.example.security.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public Skill updateSkill(Long skillId, Skill updatedSkill) throws Exception {
        Optional<Skill> existingSkillOptional = skillRepository.findById(skillId);
        if (existingSkillOptional.isEmpty()) {
            throw new Exception("Skill not found");
        }

        Skill existingSkill = existingSkillOptional.get();

        // Update the necessary fields of the existing skill with the new data
        existingSkill.setSkillName(updatedSkill.getSkillName());
        existingSkill.setSkillScore(updatedSkill.getSkillScore());

        // Save the updated skill in the repository
        return skillRepository.save(existingSkill);
    }
}
