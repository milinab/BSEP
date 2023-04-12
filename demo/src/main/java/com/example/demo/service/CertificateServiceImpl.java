package com.example.demo.service;

import com.example.demo.dto.CertificateIssuerDTO;
import com.example.demo.dto.CertificateStatusDTO;
import com.example.demo.keystores.KeyStoreReader;
import com.example.demo.keystores.KeyStoreWriter;
import com.example.demo.model.Certificate;
import com.example.demo.model.*;
import com.example.demo.repository.CertificateIssuerRepository;
import com.example.demo.repository.CertificateRepository;
import com.example.demo.repository.CertificateStatusRepository;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateRepository certificateRepository;
    private CertificateIssuerRepository certificateIssuerRepository;
    private CertificateStatusRepository certificateStatusRepository;
    private KeyStoreReader keyStoreReader;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, KeyStoreReader keyStoreReader,
                                  CertificateIssuerRepository certificateIssuerRepository, CertificateStatusRepository certificateStatusRepository){
        this.certificateRepository = certificateRepository;
        this.certificateIssuerRepository = certificateIssuerRepository;
        this.certificateStatusRepository = certificateStatusRepository;
        this.keyStoreReader = keyStoreReader;
    }

    @Override
    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }


    @Override
    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public void writingCertificateInFile(KeyPair keyPair, CertificateData certificateData, KeyStore keyStore, X509Certificate certificate, String alias){
        String password = certificateData.getKeyStorePassword();
        String fileName = certificateData.getKeyStoreName();
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


    @Override
    public Issuer generateIssuer(PrivateKey issuerKey, CertificateData intermediateCertificate) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, intermediateCertificate.getCommonName());
        builder.addRDN(BCStyle.O, intermediateCertificate.getOrganization());
        builder.addRDN(BCStyle.OU, intermediateCertificate.getLocation().getCountry());
        builder.addRDN(BCStyle.OU, intermediateCertificate.getLocation().getState());
        builder.addRDN(BCStyle.OU, intermediateCertificate.getLocation().getCity());
        return new Issuer(issuerKey, builder.build());
    }
    @Override
    public Subject generateSubject(CertificateData intermediateCertificate) {
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

    @Override
    public KeyPair generateKeyPair() {
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

    @Override
    public Boolean invalidateCertificate(String keyStoreFile, String keyStorePass, String alias) {
        /*//Optional<Certificate> certificate = keyStoreReader.readCertificate(keyStoreFile , keyStorePass, alias);
        Optional<Certificate> certificate = certificateRepository.findById(alias);
        Date endDate = certificate.get().getEndDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(localEndDate.isBefore(LocalDate.now()) || certificate.get().getCertificateStatus().equals(CertificateStatus.EXPIRED) ||
                certificate.get().getCertificateStatus().equals(CertificateStatus.REVOKED))
        {
            return false;
        }
        certificate.get().setCertificateStatus(CertificateStatus.REVOKED);
        certificateRepository.save(certificate.get());*/
        return true;
    }

    @Override
    public CertificateIssuerDTO saveIssuer(CertificateIssuerDTO ciDTO){
        ciDTO.setId(UUID.randomUUID().toString());
        return certificateIssuerRepository.save(ciDTO);
    }

    @Override
    public CertificateStatusDTO saveCertificateStatus(CertificateStatusDTO csDTO){
        csDTO.setId(UUID.randomUUID().toString());
        return certificateStatusRepository.save(csDTO);
    }

    @Override
    public List<CertificateIssuerDTO> findAllIssuers(){ return certificateIssuerRepository.findAll();}

    @Override
    public CertificateIssuerDTO findByAlias(String alias) {
        return certificateIssuerRepository.findByAlias(alias);
    }

    @Override
    public CertificateStatusDTO findCertificateStatusByAlias(String alias) {
        return certificateStatusRepository.findCertificateStatusByAlias(alias);
    }
}
