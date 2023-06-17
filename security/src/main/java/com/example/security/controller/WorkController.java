package com.example.security.controller;

import com.example.security.dto.WorkDto;
import com.example.security.model.AppUser;
import com.example.security.model.Work;
import com.example.security.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/work")
public class WorkController {

    private final WorkService workService;
    Logger logger = LoggerFactory.getLogger(WorkController.class);

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addWorkersToProjects")
    public void addWorkersToProjects(@RequestBody WorkDto workDto){
        logger.info("Calling addWorkersToProjects endpoint");
        logger.debug("ID SA FRONTA: {}", workDto.getProjectId());
        workService.addWorkersToProject(workDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{workerId}")
    public ResponseEntity<List<Work>> getWorksByWorkerId(@PathVariable Long workerId) {
        logger.info("Calling getWorksByWorkerId endpoint");
        List<Work> works = workService.getWorksByWorkerId(workerId);
        return ResponseEntity.ok(works);
    }

    @GetMapping("/current/{workerId}")
    public ResponseEntity<List<Work>> getCurrentWorksByWorkerId(@PathVariable Long workerId) {
        logger.info("Calling getCurrentWorksByWorkerId endpoint");
        List<Work> works = workService.getCurrentWorksByWorkerId(workerId);
        return ResponseEntity.ok(works);
    }

    @GetMapping("/past/{workerId}")
    public ResponseEntity<List<Work>> getPastWorksByWorkerId(@PathVariable Long workerId) {
        logger.info("Calling getPastWorksByWorkerId endpoint");
        List<Work> works = workService.getPastWorksByWorkerId(workerId);
        return ResponseEntity.ok(works);
    }

    @PutMapping("/description/{id}")
    public ResponseEntity<String> updateWork(@PathVariable Long id, @RequestBody Work updatedWork) {
        logger.info("Calling updateWork endpoint");
        try {
            Work updatedEntity = workService.updateWork(id, updatedWork);
            return ResponseEntity.ok("Work updated successfully");
        } catch (Exception e) {
            logger.error("Error updating work", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/workersByProject/{id}")
    public List<AppUser> getAllWorkersByProject(@PathVariable("id") Long projectId){
        logger.info("Calling getAllWorkersByProject endpoint");
        return workService.getAllWorkersByProject(projectId);
    }

//    @PutMapping(consumes = "application/json", value = "/dismiss/{id}")
//    public ResponseEntity<Work> dismiss(@PathVariable("id") Long id) {
//        Boolean appointment = appointmentService.cancelAppointment(id);
//        if (appointment== false){
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
