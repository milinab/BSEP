package com.example.demo.controller;


import com.example.demo.dto.CertificateDTO;
import com.example.demo.model.Certificate;
import com.example.demo.model.IntermediateCertificate;
import com.example.demo.model.Issuer;
import com.example.demo.model.Subject;
import com.example.demo.service.CertificateService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.*;
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


    private Issuer generateIssuer(PrivateKey issuerKey, IntermediateCertificate intermediateCA) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, intermediateCA.getCommonName());
        builder.addRDN(BCStyle.O, intermediateCA.getOrganization());
        builder.addRDN(BCStyle.OU, intermediateCA.getOrganization());
        builder.addRDN(BCStyle.C, intermediateCA.getLocation().getCountry());
        return new Issuer(issuerKey, builder.build());
    }

    private Subject generateSubject(IntermediateCertificate intermediateCA) {

        KeyPair keyPairSubject = generateKeyPair();


        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate;

        if(intermediateCA.isValid())
        {
            endDate.plusYears(1);
        }



        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, intermediateCA.getCommonName());
        builder.addRDN(BCStyle.O, intermediateCA.getOrganization());
        builder.addRDN(BCStyle.C, intermediateCA.getLocation().getCountry());
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
