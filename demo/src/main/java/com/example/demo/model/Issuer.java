package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Issuer extends User {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private X500Name x500Name;

    public Issuer(PrivateKey issuerKey, X500Name build) {
        this.privateKey = issuerKey;
        this.x500Name = build;
    }
}
