package com.example.security.service;

import com.example.security.model.AppUser;
import com.example.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    public AppUser edit(AppUser user, Long id) {
        Optional<AppUser> editUser = userRepository.findById(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser editedUser = userRepository.save(user);
        return editUser.isEmpty() ? null : editedUser;
    }
    public AppUser findById(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }
}