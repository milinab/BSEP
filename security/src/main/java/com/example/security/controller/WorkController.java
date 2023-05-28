package com.example.security.controller;

import com.example.security.dto.WorkDto;
import com.example.security.model.AppUser;
import com.example.security.model.Work;
import com.example.security.service.WorkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/work")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping("/addWorkersToProjects")
    public void addWorkersToProjects(@RequestBody WorkDto workDto){
        System.out.println("ID SA FRONTA:  " + workDto.getProjectId());
        workService.addWorkersToProject(workDto);
    }

    @GetMapping("/{workerId}")
    public ResponseEntity<List<Work>> getWorksByWorkerId(@PathVariable Long workerId) {
        List<Work> works = workService.getWorksByWorkerId(workerId);
        return ResponseEntity.ok(works);
    }

    @PutMapping("/description/{id}")
    public ResponseEntity<String> updateWork(@PathVariable Long id, @RequestBody Work updatedWork) {
        try {
            Work updatedEntity = workService.updateWork(id, updatedWork);
            return ResponseEntity.ok("Work updated successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/workersByProject/{id}")
    public List<AppUser> getAllWorkersByProject(@PathVariable("id") Long projectId){
        return workService.getAllWorkersByProject(projectId);
    }
}
