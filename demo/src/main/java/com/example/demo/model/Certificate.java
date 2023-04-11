package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.security.cert.X509Certificate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@Entity
public class Certificate {
    @Generated
    @Id
    private String id;
    private String subject;
    private String issuer;
    private String serialNumber;
    private Boolean isExpired;
    private Date startDate;
    private Date endDate;
    private CertificateStatus certificateStatus;
    private CertificateType certificateType;
    private String alias;
    private String organization;
    private String commonName;
    private String category;
    private String keyStoreName;
    private String keyStorePassword;
    // svi prethodni podaci mogu da se izvuku i iz X509Certificate, osim privatnog kljuca issuera
  // private X509Certificate x509Certificate;

    public Certificate() {

    }
}
