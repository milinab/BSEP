package com.example.demo.controller;


import com.example.demo.dto.CertificateDTO;
import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
