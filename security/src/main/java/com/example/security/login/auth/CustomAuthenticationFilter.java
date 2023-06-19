package com.example.security.login.auth;

import com.auth0.jwt.JWT;
import com.example.security.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");

        if (!appUserService.isBlocked(username)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
        } else {
            throw new AppUserIsBlockedException();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication)
            throws IOException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))    // 15 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", user.getAuthorities().stream().
                        map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(AuthUtility.getAlgorithm());
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))    // 14 days
                .withIssuer(request.getRequestURL().toString())
                .sign(AuthUtility.getAlgorithm());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        Cookie jwtCookie = new Cookie("refreshToken", refreshToken);
        jwtCookie.setMaxAge(14 * 24 * 60 * 60); // 14 days
        jwtCookie.setSecure(true);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        response.setContentType(APPLICATION_JSON_VALUE);
        logger.info("User with IP: {} successfully authenticated", request.getRemoteAddr());
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException {
        Map<String, String> error = new HashMap<>();
        String email = request.getParameter("email");
        if (failed instanceof DisabledException) {
            logger.warn("User with email {} tried to log in, but his email is not confirmed.", email);
            error.put("errorMessage", "Email address not confirmed");
        } else if (failed instanceof AppUserIsBlockedException) {
            logger.warn("Blocked user with email: {} tried to log in.", email);
            error.put("errorMessage", "User is blocked");
        }
        else {
            logger.warn("User failed to log in from IP: {} , reason: incorrect credentials", request.getRemoteAddr());
            error.put("errorMessage", "Incorrect email or password");
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}