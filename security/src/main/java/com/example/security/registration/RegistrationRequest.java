package com.example.security.registration;

import com.example.security.enums.AppUserRole;
import com.example.security.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final AppUserRole appUserRole;
    private final RegistrationStatus registrationStatus;
    private final String password;
}
