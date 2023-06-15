package com.example.security.controller;

import com.example.security.dto.AppUserDto;
import com.example.security.dto.EditPasswordDto;
import com.example.security.enums.AppUserRole;
import com.example.security.enums.RegistrationStatus;
import com.example.security.model.AppUser;
import com.example.security.service.AppUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/appUser")
@AllArgsConstructor
public class AppUserController {
    private AppUserService appUserService;
    //private Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @GetMapping(path = "pending")
    public List<AppUser> getPendingUsers() {
        //logger.info("Getting pending users");
        return appUserService.getUsersByRegistrationStatus(RegistrationStatus.PENDING);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateAppUser(@PathVariable("email") String email, @RequestBody AppUser updatedUser) {
        try {
            //logger.info("AppUser with email: {}, updated successfully", email);
            AppUser updatedAppUser = appUserService.updateAppUser(email, updatedUser);
            return ResponseEntity.ok("AppUser updated successfully");
        } catch (Exception e) {
            //logger.error("Error updating AppUser with email: {}", email);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<AppUserDto> getAppUserByEmail(
            @PathVariable("email") String email
    ) {
        try {
            //logger.info("Getting AppUser by email {}", email);
            return ResponseEntity.ok(appUserService.getAppUserByEmail(email));

        } catch (Exception e) {
            //logger.error("Error getting AppUser by email {}", email);
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "engineer")
    public List<AppUser> getEngineerUsers() {
        //logger.info("Getting engineer users");
        return appUserService.getUsersByUserRole(AppUserRole.SOFTWARE_ENGINEER);
    }

    @GetMapping("/search")
    public List<AppUser> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String startDateString,
            @RequestParam(required = false) String endDateString
    ) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (startDateString != null && !startDateString.isEmpty()) {
            startDate = LocalDate.parse(startDateString);
        }

        if (endDateString != null && !endDateString.isEmpty()) {
            endDate = LocalDate.parse(endDateString);
        }

        return appUserService.searchUsers(firstName, lastName, email, startDate, endDate);
    }

    @PutMapping("/block/{email}")
    public ResponseEntity blockUser(@PathVariable("email") String email){
        appUserService.blockUser(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/unblock/{email}")
    public ResponseEntity unblockUser(@PathVariable("email") String email){
        appUserService.unblockUser(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity updatePassword(@RequestBody EditPasswordDto editPasswordDto){
        appUserService.updatePassword(editPasswordDto);
        return new ResponseEntity(HttpStatus.OK);
    }


}
