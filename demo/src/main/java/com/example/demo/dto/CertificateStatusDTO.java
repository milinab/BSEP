package com.example.demo.dto;

import com.example.demo.model.CertificateStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CertificateStatusDTO {

    @Id
    private String id;
    private String alias;
    private CertificateStatus status;
}
