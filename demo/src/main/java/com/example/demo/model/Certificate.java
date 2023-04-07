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
    @Id @Generated
    private String id;
    private String subject;
    private String issuer;
    private String serialNumber;
    private Boolean isExpired;
    private Date startDate;
    private Date endDate;
    private String alias;

    // svi prethodni podaci mogu da se izvuku i iz X509Certificate, osim privatnog kljuca issuera
    private X509Certificate x509Certificate;

    public Certificate() {

    }
}
