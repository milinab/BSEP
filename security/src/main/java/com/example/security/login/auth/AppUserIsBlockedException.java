package com.example.security.login.auth;

import org.springframework.security.core.AuthenticationException;

public class AppUserIsBlockedException extends AuthenticationException {
    public AppUserIsBlockedException() {
        super("User is blocked");
    }
}
