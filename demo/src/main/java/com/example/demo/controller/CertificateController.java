package com.example.demo.controller;


import com.example.demo.certificates.CertificateGenerator;
import com.example.demo.dto.CertificateDTO;
import com.example.demo.keystores.KeyStoreWriter;
import com.example.demo.model.*;
import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/certificate")
public class CertificateController {
    private final CertificateService certificateService;


    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }



    @GetMapping(value = "/all")
    public ResponseEntity<List<CertificateDTO>> getAllCertificates(){
        List<Certificate> certificates = certificateService.getAll();

        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates){
            certificateDTOS.add(new CertificateDTO(certificate));
        }
        return new ResponseEntity<>(certificateDTOS, HttpStatus.OK);
    }


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
            Subject subject = generateSubject(certificateData);
            KeyPair keyPair = generateKeyPair();
            Issuer issuer = generateIssuer(keyPair.getPrivate(), certificateData);
            X509Certificate certificate = new CertificateGenerator().generateCertificate(subject, issuer, certificateData.getStartDate(), certificateData.getEndDate(), certificateData.getSerialNumber());
            writingCertificateInFile(keyPair, certificateData, KeyStore.getInstance("JKS", "SUN"), certificate);
            return new ResponseEntity<>(new Certificate(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private void writingCertificateInFile(KeyPair keyPair,CertificateData certificateData, KeyStore keyStore, X509Certificate certificate) throws FileNotFoundException {
        String password = certificateData.getKeyStorePassword();
        String fileName = certificateData.getKeyStoreName();
        String alias = certificateData.getAlias();
        BufferedInputStream in = null;

        try {
            if(certificateData.getType().equals(CertificateType.ROOT)) {
                fileName = "root";
                in = new BufferedInputStream(new FileInputStream("src/main/resources/static/" +fileName+".jks"));
            } else if(certificateData.getType().equals(CertificateType.INTERMEDIARY)) {
                fileName = "intermediary";
                in = new BufferedInputStream(new FileInputStream("src/main/resources/static/" +fileName+".jks"));
            } else if(certificateData.getType().equals(CertificateType.END_ENTITY)) {
                fileName = "endEntity";
                in = new BufferedInputStream(new FileInputStream("src/main/resources/static/" +fileName+".jks"));
            }
        }
        catch(Exception e){
            System.out.println("prvi");
        }
        if (in ==null) {
            KeyStoreWriter ksw = new KeyStoreWriter();
            char[] pass = password.toCharArray();
            ksw.saveKeyStore(fileName, pass);
            try {
                if(certificateData.getType().equals(CertificateType.ROOT)) {
                    fileName = "root";
                    in = new BufferedInputStream(new FileInputStream("src/main/resources/static/" +fileName+".jks"));
                } else if(certificateData.getType().equals(CertificateType.INTERMEDIARY)) {
                    fileName = "intermediary";
                    in = new BufferedInputStream(new FileInputStream("src/main/resources/static/" +fileName+".jks"));
                } else if(certificateData.getType().equals(CertificateType.END_ENTITY)) {
                    fileName = "endEntity";
                    in = new BufferedInputStream(new FileInputStream("src/main/resources/static/" +fileName+".jks"));
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        }
        try{
            keyStore.load(in, password.toCharArray());
            keyStore.setCertificateEntry(alias, certificate);
            keyStore.setKeyEntry(alias, keyPair.getPrivate(), password.toCharArray(),
                    new X509Certificate[] { certificate });
            if(certificateData.getType().equals(CertificateType.ROOT)) {
                fileName = "root";
                keyStore.store(new FileOutputStream("src/main/resources/static/" +fileName+".jks"), password.toCharArray());
            } else if(certificateData.getType().equals(CertificateType.INTERMEDIARY)) {
                fileName = "intermediary";
                keyStore.store(new FileOutputStream("src/main/resources/static/" +fileName+".jks"), password.toCharArray());
            } else if(certificateData.getType().equals(CertificateType.END_ENTITY)) {
                fileName = "endEntity";
                keyStore.store(new FileOutputStream("src/main/resources/static/" +fileName+".jks"), password.toCharArray());
            }
        } catch (Exception e) {
            System.out.println("bbbbbbbbbbbb");
        }
    }



    private Issuer generateIssuer(PrivateKey issuerKey, CertificateData intermediateCertificate) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, intermediateCertificate.getCommonName());
        builder.addRDN(BCStyle.O, intermediateCertificate.getOrganization());
        builder.addRDN(BCStyle.OU, intermediateCertificate.getLocation().getCountry());
        return new Issuer(issuerKey, builder.build());
    }

    private Subject generateSubject(CertificateData intermediateCertificate) {
        KeyPair keyPairSubject = generateKeyPair();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate;
        if(intermediateCertificate.isValid())
        {
            endDate.plusYears(1);
        }
        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, intermediateCertificate.getCommonName());
        builder.addRDN(BCStyle.O, intermediateCertificate.getOrganization());
        builder.addRDN(BCStyle.C, intermediateCertificate.getLocation().getCountry());
        return new Subject(keyPairSubject.getPublic(), builder.build());
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
