package com.example.security.login.auth;

import com.example.security.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "https://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUtility authUtility;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AppUserService appUserService) {
        authUtility = new AuthUtility(appUserService);
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String accessToken = authUtility.createJWTFromRequest(request);
            if (accessToken != null) {
                response.setContentType(APPLICATION_JSON_VALUE);
                AuthUtility.setResponseMessage(response, "accessToken", accessToken);
                logger.info("User successfully refreshed their authentication token, from IP: {}", request.getRemoteAddr());
            }
            else {
                response.setStatus(UNAUTHORIZED.value());
                AuthUtility.setResponseMessage(response, "errorMessage", "Refresh token is missing");
                logger.warn("User failed to refresh their authentication token, from IP: {}, reason: Refresh token is missing.", request.getRemoteAddr());
            }
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            AuthUtility.setResponseMessage(response, "errorMessage", e.getMessage());
            logger.warn("User failed to refresh their authentication token, from IP: {}", request.getRemoteAddr());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie jwtCookie = new Cookie("refreshToken", "");
        jwtCookie.setMaxAge(0);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        AuthUtility.setResponseMessage(response, "Success", "Cookie removed");
    }
}