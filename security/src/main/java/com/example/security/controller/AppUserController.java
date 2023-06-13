package com.example.security.controller;

import com.example.security.enums.AppUserRole;
import com.example.security.enums.RegistrationStatus;
import com.example.security.model.AppUser;
import com.example.security.model.Work;
import com.example.security.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/appUser")
@AllArgsConstructor
public class AppUserController {
    private AppUserService appUserService;

    @GetMapping(path = "pending")
    public List<AppUser> getPendingUsers() {
        return appUserService.getUsersByRegistrationStatus(RegistrationStatus.PENDING);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateAppUser(@PathVariable("email") String email, @RequestBody AppUser updatedUser) {
        try {
            AppUser updatedAppUser = appUserService.updateAppUser(email, updatedUser);
            return ResponseEntity.ok("AppUser updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<AppUser> getAppUserByEmail(
            @PathVariable("email") String email
    ) {
        try {
            return ResponseEntity.ok(appUserService.getAppUserByEmail(email));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "engineer")
    public List<AppUser> getEngineerUsers() {
        return appUserService.getUsersByUserRole(AppUserRole.SOFTWARE_ENGINEER);
    }

    @GetMapping("/search")
    public List<AppUser> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String startDateString
    ) {
        LocalDate startDate = null;
        if (startDateString != null && !startDateString.isEmpty()) {
            startDate = LocalDate.parse(startDateString);
        }

        return appUserService.searchUsers(firstName, lastName, email, startDate);
    }

}
