package com.example.demo.dto;

import com.example.demo.model.Certificate;
import com.example.demo.model.Issuer;
import com.example.demo.model.Subject;
import lombok.*;

import java.security.cert.X509Certificate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CertificateDTO {
    private Subject subject;
    private Issuer issuer;
    private String serialNumber;
    private Date startDate;
    private Date endDate;
    private boolean isExpired;
    private X509Certificate x509Certificate;

    public CertificateDTO(Certificate certificate) {
        this(certificate.getSubject(), certificate.getIssuer(), certificate.getSerialNumber(), certificate.getStartDate(), certificate.getEndDate(), certificate.getIsExpired(), certificate.getX509Certificate());
    }


}
