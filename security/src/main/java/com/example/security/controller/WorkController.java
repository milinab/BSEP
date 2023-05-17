package com.example.security.controller;

import com.example.security.dto.WorkDto;
import com.example.security.service.WorkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/work")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping("/addWorkersToProjects")
    public void addWorkersToProjects(@RequestBody WorkDto workDto){
        workService.addWorkersToProject(workDto);
    }

}
