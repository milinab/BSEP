package com.example.security.dto;

import lombok.Data;

@Data
public class EditPasswordDto {
    private String email;
    private String editedPassword;
}
