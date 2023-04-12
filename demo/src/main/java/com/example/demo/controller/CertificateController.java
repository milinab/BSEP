package com.example.demo.controller;


import com.example.demo.certificates.CertificateGenerator;
import com.example.demo.dto.CertificateIssuerDTO;
import com.example.demo.keystores.KeyStoreReader;
import com.example.demo.model.*;
import com.example.demo.service.CertificateService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/certificate")
public class CertificateController {

    private final CertificateService certificateService;
    private KeyStoreReader keyStoreReader;


    public CertificateController(CertificateService certificateService, KeyStoreReader keyStoreReader) {
        this.certificateService = certificateService;
        this.keyStoreReader = keyStoreReader;
    }

    @GetMapping(value = "root-certs")
    public ResponseEntity<List<String>> getRootCertificates(){
        String keyStoreFile="src/main/resources/static/root.jks";
        String keyStorePass = "password";
        List<String> certificates = keyStoreReader.readAllCertificates(keyStoreFile, keyStorePass);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }
    @GetMapping(value = "intermediary-certs")
    public ResponseEntity<List<String>> getIntermediaryCertificates(){
        String keyStoreFile="src/main/resources/static/intermediary.jks";
        String keyStorePass = "password";
        List<String> certificates = keyStoreReader.readAllCertificates(keyStoreFile, keyStorePass);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }
    @GetMapping(value = "end-entity-certs")
    public ResponseEntity<List<String>> getEndEntityCertificates(){
        String keyStoreFile="src/main/resources/static/endEntity.jks";
        String keyStorePass = "password";
        List<String> certificates = keyStoreReader.readAllCertificates(keyStoreFile, keyStorePass);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }
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
    public ResponseEntity<Certificate> create(@RequestBody List<CertificateData> certificateData) throws ConstraintViolationException, KeyStoreException, NoSuchProviderException, FileNotFoundException {
        CertificateIssuerDTO certificateIssuer = new CertificateIssuerDTO();
        certificateIssuer.setCommonName(certificateData.get(1).getCommonName());
        certificateIssuer.setOrganization(certificateData.get(1).getOrganization());
        if(certificateData.get(0).getType().equals(CertificateType.ROOT)) {
            certificateIssuer.setType(true);
        } else {
            certificateIssuer.setType(false);
        }
        certificateService.saveIssuer(certificateIssuer);
        try {
            Subject subject = certificateService.generateSubject(certificateData.get(0));
            KeyPair keyPair = certificateService.generateKeyPair();
            Issuer issuer = certificateService.generateIssuer(keyPair.getPrivate(), certificateData.get(1));
            X509Certificate certificate = new CertificateGenerator().generateCertificate(subject, issuer, certificateData.get(0).getStartDate(), certificateData.get(0).getEndDate(), "65");
            certificateService.writingCertificateInFile(keyPair, certificateData.get(0), KeyStore.getInstance("JKS", "SUN"), certificate);

            return new ResponseEntity<>(new Certificate(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(consumes = "application/json", value = "/invalidate/{alias}")
    public ResponseEntity<Certificate> invalidateCertificate(@PathVariable("alias") String alias, @RequestParam String keyStoreFile, @RequestParam String keyStorePass) {
        Boolean certificate = certificateService.invalidateCertificate(alias, keyStoreFile, keyStorePass);
        if (certificate== false){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/createCertificateIssuer")
    public ResponseEntity<CertificateIssuerDTO> createCertificateIssuer(@RequestBody CertificateIssuerDTO ciDTO) {
        CertificateIssuerDTO newCertificateIssuerDTO = null;
        try {
            newCertificateIssuerDTO = certificateService.saveIssuer(ciDTO);
            if(newCertificateIssuerDTO == null) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<CertificateIssuerDTO>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CertificateIssuerDTO>(newCertificateIssuerDTO, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/allCertificateIssuers")
    public ResponseEntity<List<CertificateIssuerDTO>> GetAllCertificateIssuers() {
        List<CertificateIssuerDTO> issuerList = certificateService.findAllIssuers();
        return new ResponseEntity<>(issuerList, HttpStatus.OK);
    }

}
