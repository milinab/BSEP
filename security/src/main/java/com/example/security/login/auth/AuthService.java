//package com.example.security.login.auth;
//
//import com.example.security.email.EmailSender;
//import com.example.security.enums.RegistrationStatus;
//import com.example.security.login.config.JwtService;
//import com.example.security.model.AppUser;
//import com.example.security.registration.EmailValidator;
//import com.example.security.registration.token.ConfirmationToken;
//import com.example.security.registration.token.ConfirmationTokenService;
//import com.example.security.repository.AppUserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final AppUserRepository repository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private EmailValidator emailValidator;
//    private final EmailSender emailSender;
//
//    private final ConfirmationTokenService confirmationTokenService;
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//         authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//
//        var user = repository.findByEmail(request.getEmail()).orElseThrow();
//        if(user.getRegistrationStatus() == RegistrationStatus.DENIED || user.getRegistrationStatus() == RegistrationStatus.PENDING) {
//            throw new DisabledException("Your account has been disabled. Please contact the administrator for more information.");
//        }
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//}
//
//
//
//
