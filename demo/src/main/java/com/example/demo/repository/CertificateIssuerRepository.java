package com.example.demo.repository;

import com.example.demo.dto.CertificateIssuerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateIssuerRepository extends JpaRepository<CertificateIssuerDTO, String> {
}
