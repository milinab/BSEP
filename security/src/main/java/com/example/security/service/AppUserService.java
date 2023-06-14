package com.example.security.service;

import com.example.security.dto.AppUserDto;
import com.example.security.enums.AppUserRole;
import com.example.security.enums.RegistrationStatus;
import com.example.security.model.AppUser;
import com.example.security.registration.token.ConfirmationToken;
import com.example.security.registration.token.ConfirmationTokenService;
import com.example.security.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getAppUserRole().toString()));
        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(),
                appUser.getPassword(),
                authorities) {
            @Override
            public boolean isEnabled() {
                return appUser.getEnabled();
            }
        };
    }

    public AppUser getUserById(Long userId) {
        Optional<AppUser> userOptional = appUserRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public void saveUser(AppUser appUser){
        appUserRepository.save(appUser);
    }

    public AppUser updateAppUser(String email, AppUser updatedUser) throws Exception {
        Optional<AppUser> existingUserOptional = appUserRepository.findByEmail(email);
        if (existingUserOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        AppUser existingUser = existingUserOptional.get();

        // Update the necessary fields of the existing user with the new data
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
        existingUser.setPassword(encodedPassword);
        existingUser.setAppUserRole(updatedUser.getAppUserRole());

        // Save the updated user in the repository
        return appUserRepository.save(existingUser);
    }

    public List<AppUser> getUsersByRegistrationStatus(RegistrationStatus registrationStatus) {
        return appUserRepository.findByRegistrationStatus(registrationStatus);
    }


    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public AppUserDto getAppUserByEmail (String email) throws Exception{
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new Exception("App user is not found with Email: " + email);
        }
        AppUser user = appUser.get();
        AppUserDto userDto = new AppUserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setAppUserRole(user.getAppUserRole());

        return userDto;
    }

    public List<AppUser> getUsersByUserRole(AppUserRole userRole) {
        return appUserRepository.findByAppUserRole(userRole);
    }

    public List<AppUser> searchUsers(String firstName, String lastName, String email, LocalDate startDate, LocalDate endDate) {
        List<AppUser> allEngineers = getUsersByUserRole(AppUserRole.SOFTWARE_ENGINEER);
        List<AppUser> matchingUsers = new ArrayList<>();

        for (AppUser user : allEngineers) {
            boolean match = true;

            if (firstName != null && !firstName.isEmpty()) {
                match = match && user.getFirstName() != null && user.getFirstName().toLowerCase().contains(firstName.toLowerCase());
            }

            if (lastName != null && !lastName.isEmpty()) {
                match = match && user.getLastName() != null && user.getLastName().toLowerCase().contains(lastName.toLowerCase());
            }

            if (email != null && !email.isEmpty()) {
                match = match && user.getEmail() != null && user.getEmail().toLowerCase().contains(email.toLowerCase());
            }

            if (startDate != null && endDate != null) {
                match = match && user.getStartDate() != null && user.getStartDate().isAfter(startDate) && user.getStartDate().isBefore(endDate);
            }

            if (match) {
                matchingUsers.add(user);
            }
        }

        return matchingUsers;
    }


}
