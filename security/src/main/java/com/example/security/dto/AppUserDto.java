package com.example.security.dto;

import com.example.security.enums.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private AppUserRole appUserRole;
}
