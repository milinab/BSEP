package com.example.demo.service;

import com.example.demo.model.Certificate;
import com.example.demo.model.CertificateData;
import com.example.demo.model.Issuer;
import com.example.demo.model.Subject;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;


public interface CertificateService {

    List<Certificate> getAll();

    Certificate save(Certificate certificate);

    Issuer generateIssuer(PrivateKey issuerKey, CertificateData intermediateCertificate);

    void writingCertificateInFile(KeyPair keyPair, CertificateData certificateData, KeyStore keyStore, X509Certificate certificate);

    Subject generateSubject(CertificateData intermediateCertificate);

    KeyPair generateKeyPair();

}
