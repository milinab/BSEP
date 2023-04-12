package com.example.demo.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertificateData {
    @Id
    private String  id;
    private String organization;
    private int serialNumber;
    private String commonName;
    private String alias;
    private boolean isValid;
    private String category;
    private Location location;
    private String keyStoreName;
    private String keyStorePassword;
    private Date startDate;
    private Date endDate;
    private CertificateType type;
    private CertificateStatus status;
}
