package com.example.demo.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IntermediateCertificate {
    @Id
    private String  id;
    private String organization;
    private String serialNumber;
    private String commonName;
    private String alias;
    private boolean isValid;
    private String category;
    private Location location;
}
