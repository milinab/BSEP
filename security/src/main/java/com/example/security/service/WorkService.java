package com.example.security.service;

import com.example.security.dto.WorkDto;
import com.example.security.model.AppUser;
import com.example.security.model.Project;
import com.example.security.model.Work;
import com.example.security.repository.ProjectRepository;
import com.example.security.repository.UserRepository;
import com.example.security.repository.WorkRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public WorkService(WorkRepository workRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.workRepository = workRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void addWorkersToProject(WorkDto workDto){
        Project project  = projectRepository.findById(workDto.getProjectId()).orElseThrow(() -> new EntityNotFoundException("Project is not found"));
        List<AppUser> workers = userRepository.findAllById(workDto.getWorkersId());

        for(AppUser worker : workers){
            Work work = new Work();
            work.setWorker(worker);
            work.setProject(project);
            workRepository.save(work);
        }

    }


}
