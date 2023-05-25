package com.example.security.controller;

import com.example.security.registration.RegistrationRequest;
import com.example.security.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @PostMapping(path = "pending")
    public String pendingRegister(@RequestBody RegistrationRequest request){
        return registrationService.pendingRegister(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping(path = "{userId}/deny")
    public void denyRegistration(@PathVariable("userId") Long userId, @RequestParam("reason") String denialReason) {
        registrationService.denyRegistration(userId, denialReason);
    }
}
