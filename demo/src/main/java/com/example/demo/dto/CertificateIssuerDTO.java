package com.example.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "issuer")
public class CertificateIssuerDTO {

    @Id
    private String id;
    private String alias;
    private String organization;
    private String commonName;
    private Boolean type;
}
