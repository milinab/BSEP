package com.example.security.controller;

import com.example.security.dto.UserDto;
import com.example.security.model.AppUser;
import com.example.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AppUser> getAll(){
        return userService.getAll();
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user){
        logger.info("Creating user with email: {}", user.getUsername());
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody AppUser user, @PathVariable("id") Long id){
        logger.info("Updating user with ID: {}", user.getId());
        AppUser editUser = userService.edit(user, id);
        if (editUser == null) {
            logger.warn("Failed to update user with ID: {}, reason: User not found", user.getId());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            logger.info("User with ID: {} updated successfully", user.getId());
            return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public AppUser findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
}
