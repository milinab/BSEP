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
        return userService.getAll();
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody AppUser user, @PathVariable("id") Long id){
        AppUser editUser = userService.edit(user, id);
        if (editUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public AppUser findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
}
