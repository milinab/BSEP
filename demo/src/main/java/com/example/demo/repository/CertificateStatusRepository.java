package com.example.demo.repository;

import com.example.demo.dto.CertificateStatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateStatusRepository extends JpaRepository<CertificateStatusDTO, String> {

    CertificateStatusDTO findCertificateStatusByAlias(String alias);
}
