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
import java.util.Optional;

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
        logger.info("Returning all users");
        return userService.getAll();
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user){
        logger.info("Creating user: {}", user.getUsername());
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody AppUser user, @PathVariable("id") Long id){
        logger.info("Updating user: {}", user.getUsername());
        AppUser editUser = userService.edit(user, id);
        if (editUser == null) {
            logger.warn("User not found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            logger.info("User updated successfully");
            return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public AppUser findById(@PathVariable("id") Long id) {
        logger.info("Finding user by ID");
        return userService.findById(id);
    }
}
