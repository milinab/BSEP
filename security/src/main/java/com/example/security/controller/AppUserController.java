package com.example.security.controller;

import com.example.security.enums.RegistrationStatus;
import com.example.security.model.AppUser;
import com.example.security.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/appUser")
@AllArgsConstructor
public class AppUserController {
    private AppUserService appUserService;

    @GetMapping(path = "pending")
    public List<AppUser> getPendingUsers() {
        return appUserService.getUsersByRegistrationStatus(RegistrationStatus.PENDING);
    }

}
