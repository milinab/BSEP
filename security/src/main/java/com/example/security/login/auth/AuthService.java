package com.example.security.login.auth;

import com.example.security.enums.RegistrationStatus;
import com.example.security.login.config.JwtService;
import com.example.security.model.AppUser;
import com.example.security.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        if(user.getRegistrationStatus() == RegistrationStatus.DENIED || user.getRegistrationStatus() == RegistrationStatus.PENDING) {
            throw new DisabledException("Your account has been disabled. Please contact the administrator for more information.");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
