package com.example.demo.keystores;

import com.example.demo.certificates.CertificateGenerator;
import com.example.demo.model.*;
import com.example.demo.model.Certificate;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component

public class KeyStoreGenerate {
    @Autowired
    private KeyStoreWriter keyStoreWriter;
    private String fileName;
    public void GenerateInitialData(){
        KeyPair keyPairSubject = generateKeyPair();

        Subject subject = generateSubject(new CreateCertificateData("Milina Bucko", "Bucko", "Milina", "FTN",
                "FTN-org", "RS", "milina@uns.ac.rs"), keyPairSubject.getPublic());
        Issuer issuer = generateIssuer(new CreateCertificateData("Milina Bucko", "Bucko", "Milina", "FTN",
                "FTN-org", "RS", "milina@uns.ac.rs"), keyPairSubject.getPublic(), keyPairSubject.getPrivate());
        CertificateInit rootCertificate = getCertificate(subject, issuer);
        fileName = "root";
        keyStoreWriter.loadKeyStore("src/main/resources/static/" +fileName+".jks", "password".toCharArray());
        keyStoreWriter.write("1", issuer.getPrivateKey(), "password".toCharArray(), rootCertificate.getX509Certificate());
    }
    public Subject generateSubject(CreateCertificateData createCertificateData, PublicKey subjectPublicKey) {

        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, createCertificateData.getCommonName());
        builder.addRDN(BCStyle.SURNAME, createCertificateData.getSurname() );
        builder.addRDN(BCStyle.GIVENNAME, createCertificateData.getGivenName());
        builder.addRDN(BCStyle.O, createCertificateData.getOrganization());
        builder.addRDN(BCStyle.OU, createCertificateData.getOrganizationalUnit());
        builder.addRDN(BCStyle.C, createCertificateData.getCountry());
        builder.addRDN(BCStyle.E, createCertificateData.getEmail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "123456");

        return new Subject(subjectPublicKey, builder.build());
    }

    public Issuer generateIssuer(CreateCertificateData createCertificateData, PublicKey subjectPublicKey, PrivateKey subjectPrivateKey) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, createCertificateData.getCommonName());
        builder.addRDN(BCStyle.SURNAME, createCertificateData.getSurname() );
        builder.addRDN(BCStyle.GIVENNAME, createCertificateData.getGivenName());
        builder.addRDN(BCStyle.O, createCertificateData.getOrganization());
        builder.addRDN(BCStyle.OU, createCertificateData.getOrganizationalUnit());
        builder.addRDN(BCStyle.C, createCertificateData.getCountry());
        builder.addRDN(BCStyle.E, createCertificateData.getEmail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "654321");

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new Issuer(subjectPrivateKey, subjectPublicKey, builder.build());
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

    public com.example.demo.model.CertificateInit getCertificate(Subject subject, Issuer issuer) {

        try {


            //Datumi od kad do kad vazi sertifikat
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Boolean isExpired = false;
            Date startDate = sdf.parse("2023-03-25");
            Date endDate = sdf.parse("2028-03-25");

            X509Certificate certificate = CertificateGenerator.generateCertificate(subject,
                    issuer, startDate, endDate, "1");

            return new CertificateInit(subject, issuer,
                    "1", startDate, endDate, certificate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
