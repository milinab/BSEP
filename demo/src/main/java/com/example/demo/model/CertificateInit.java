package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.cert.X509Certificate;
import java.util.Date;
@Data
@AllArgsConstructor
public class CertificateInit {
    private Subject subject;
    private Issuer issuer;
    private String serialNumber;
    private Date startDate;
    private Date endDate;

    // svi prethodni podaci mogu da se izvuku i iz X509Certificate, osim privatnog kljuca issuera
     private X509Certificate x509Certificate;
}
