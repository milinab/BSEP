package com.example.demo.controller;


import com.example.demo.certificates.CertificateGenerator;
import com.example.demo.model.Certificate;
import com.example.demo.model.CertificateData;
import com.example.demo.model.Issuer;
import com.example.demo.model.Subject;
import com.example.demo.service.CertificateService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/certificate")
public class CertificateController {
    private final CertificateService certificateService;


    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }/*
    @GetMapping(value = "allCert")
    public ResponseEntity<List<CertificateData>> getAllCertificates(String keyStore, String fileName, char[] password) {
        List<CertificateData> certificates = new ArrayList<>();
        try {
            keyStore.load(new FileInputStream(fileName), password);
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                CertificateData certificate = keyStore.getCertificate(alias);
                if (certificate != null) {
                    certificates.add(certificate);
                }
            }
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }*/
/*
    @GetMapping(value = "/all")
    public ResponseEntity<List<CertificateDTO>> getAllCertificates(){
        List<Certificate> certificates = certificateService.getAll();

        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates){
            certificateDTOS.add(new CertificateDTO(certificate));
        }
        return new ResponseEntity<>(certificateDTOS, HttpStatus.OK);
    }*/




    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Certificate> save(@RequestBody Certificate certificate) throws ConstraintViolationException{
        Certificate savedCertificate = null;
        try{
            savedCertificate = certificateService.save(certificate);
            if(savedCertificate == null){
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<Certificate>(savedCertificate, HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Certificate>(savedCertificate, HttpStatus.CONFLICT);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/create")
    public ResponseEntity<Certificate> create(@RequestBody CertificateData certificateData) throws ConstraintViolationException, KeyStoreException, NoSuchProviderException, FileNotFoundException {
        try {
            Subject subject = certificateService.generateSubject(certificateData);
            KeyPair keyPair = certificateService.generateKeyPair();
            Issuer issuer = certificateService.generateIssuer(keyPair.getPrivate(), certificateData);
            X509Certificate certificate = new CertificateGenerator().generateCertificate(subject, issuer, certificateData.getStartDate(), certificateData.getEndDate(), certificateData.getSerialNumber());
            certificateService.writingCertificateInFile(keyPair, certificateData, KeyStore.getInstance("JKS", "SUN"), certificate);
            return new ResponseEntity<>(new Certificate(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
