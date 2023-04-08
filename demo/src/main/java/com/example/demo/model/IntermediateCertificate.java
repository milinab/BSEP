package com.example.demo.model;

import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
    private String keyStoreName;
    private String keyStorePassword;
}
