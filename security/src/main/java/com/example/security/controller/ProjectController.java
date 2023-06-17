package com.example.security.controller;

import com.example.security.model.Project;
import com.example.security.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/project")
public class ProjectController {

    private final ProjectService projectService;
    Logger logger = LoggerFactory.getLogger(ProjectController.class);

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAll(){
        logger.info("Retrieving all projects");
        return projectService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Project createProject(@RequestBody Project project){
        logger.info("POST /api/v1/project - Creating a new project");
        Project createdProject = projectService.createProject(project);
        logger.info("POST /api/v1/project - Created new project with ID: {}", createdProject.getId());
        return createdProject;
    }

}
