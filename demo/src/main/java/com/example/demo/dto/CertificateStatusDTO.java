package com.example.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "certificate_status")
public class CertificateStatusDTO {

    @Id
    private String id;
    private String alias;
    private Boolean status;
}
