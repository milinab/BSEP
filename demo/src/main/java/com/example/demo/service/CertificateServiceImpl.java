package com.example.demo.service;

import com.example.demo.model.Certificate;
import com.example.demo.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateRepository certificateRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository){
        this.certificateRepository = certificateRepository;
    }

    @Override
    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }


    @Override
    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }
}
