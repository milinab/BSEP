package com.example.security.controller;

import com.example.security.model.AppUser;
import com.example.security.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AppUser> getAll(){
        return userService.getAll();
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@RequestBody AppUser user, @PathVariable("id") Long id) {
        return userService.edit(user, id);
    }
}
