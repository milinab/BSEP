package com.example.security.controller;

import com.example.security.model.Project;
import com.example.security.service.ProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAll(){
        return projectService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

}
