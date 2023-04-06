package com.example.demo.service;

import com.example.demo.model.Certificate;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CertificateService {

    List<Certificate> getAll();
    Certificate save(Certificate certificate);
}
