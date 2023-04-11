package com.example.demo.dto;

import com.example.demo.model.*;
import lombok.*;

import java.security.cert.X509Certificate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CertificateDTO {
    private String subject;
    private String issuer;
    private String serialNumber;
    private Date startDate;
    private Date endDate;
    private boolean isExpired;
    private CertificateStatus certificateStatus;
    private CertificateType certificateType;
   // private X509Certificate x509Certificate;

    public CertificateDTO(Certificate certificate) {
        this(certificate.getSubject().toString(), certificate.getIssuer().toString(), certificate.getSerialNumber(), certificate.getStartDate(), certificate.getEndDate(), certificate.getIsExpired(), certificate.getCertificateStatus(), certificate.getCertificateType()/*, certificate.getX509Certificate()*/);
    }


}
