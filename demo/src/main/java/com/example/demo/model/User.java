package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Generated
    @Id
    private String id;
    private UserType userType;
    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;



}
