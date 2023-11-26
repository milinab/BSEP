package com.example.security.service;

import com.example.security.dto.WorkDto;
import com.example.security.model.AppUser;
import com.example.security.model.Project;
import com.example.security.model.Work;
import com.example.security.repository.ProjectRepository;
import com.example.security.repository.UserRepository;
import com.example.security.repository.WorkRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public Work updateWork(Long id, Work updatedWork) throws Exception {
        Optional<Work> optionalWork = workRepository.findById(id);
        if (optionalWork.isPresent()) {
            Work work = optionalWork.get();
            work.setDescription(updatedWork.getDescription());

            return workRepository.save(work);
        } else {
            throw new Exception("Work not found with ID: " + id);
        }
    }

    public List<Work> getWorksByWorkerId(Long workerId) {
        return workRepository.findByWorkerId(workerId);
    }

    public List<Work> getCurrentWorksByWorkerId(Long workerId) {
        List<Work> allWorks = workRepository.findByWorkerId(workerId);
        List<Work> currentWorks = new ArrayList<>();
        for (Work work : allWorks) {
            if(work.getEndTime()==null) {
                currentWorks.add(work);
            }
        }
        return currentWorks;
    }

    public List<Work> getPastWorksByWorkerId(Long workerId) {
        List<Work> allWorks = workRepository.findByWorkerId(workerId);
        List<Work> pastWorks = new ArrayList<>();
        for (Work work : allWorks) {
            if(work.getEndTime()!=null) {
                pastWorks.add(work);
            }
        }
        return pastWorks;
    }


    public List<AppUser> getAllWorkersByProject(Long projectId) {
        List<Work> works = workRepository.findByProjectId(projectId);
        List<AppUser> users = new ArrayList<>();
        for(Work work: works) {
            users.add(work.getWorker());
        }
        return users;
    }
}
