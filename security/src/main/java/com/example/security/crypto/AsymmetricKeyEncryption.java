package com.example.security.crypto;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.keys.KeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.Certificate;

@Component
public class AsymmetricKeyEncryption {
    private static final String KEY_STORE_FILE = "C:/Users/Nemanja/Desktop/bsep/BSEP/security/src/main/java/com/example/security/data/example.pfx";

    static {
        // staticka inicijalizacija
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
    public void testIt(String filePath) {
        // ucitava se dokument
        String full_file_path = "C:/Users/Nemanja/Desktop/bsep/BSEP/security/src/main/java/com/example/security/data/" + filePath;
        Document doc = loadDocument(full_file_path);

        // generise tajni session kljuc
        System.out.println("Generating secret key ....");
        SecretKey secretKey = generateDataEncryptionKey();

        // ucitava sertifikat za kriptovanje tajnog kljuca
        Certificate cert = readCertificate();

        // kriptuje se dokument
        System.out.println("Encrypting....");
        doc = encrypt(doc, secretKey, cert);

        String OUT_FILE = "C:/Users/Nemanja/Desktop/bsep/BSEP/security/src/main/java/com/example/security/data/";
        String fileName = new File(full_file_path).getName();
        OUT_FILE+= "enc_";
        OUT_FILE += fileName;
        // snima se tajni kljuc
        // snima se dokument
        saveDocument(doc, OUT_FILE);

        System.out.println("Encryption done");
    }

    private Document loadDocument(String file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(file));
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Certificate readCertificate() {
        try {
            // kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("PKCS12", "SunJSSE");
            // ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
            ks.load(in, "password".toCharArray());

            if (ks.isKeyEntry("1")) {
                Certificate cert = ks.getCertificate("1");
                return cert;
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveDocument(Document doc, String fileName) {
        try {
            File outFile = new File(fileName);
            FileOutputStream f = new FileOutputStream(outFile);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);

            transformer.transform(source, result);

            f.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SecretKey generateDataEncryptionKey() {

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); // Triple
            // DES
            keyGenerator.init(256);
            return keyGenerator.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Document encrypt(Document doc, SecretKey key, Certificate certificate) {

        try {

            // cipher za kriptovanje XML-a
            XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128);

            // inicijalizacija za kriptovanje
            xmlCipher.init(XMLCipher.ENCRYPT_MODE, key);

            // cipher za kriptovanje tajnog kljuca,
            // Koristi se Javni RSA kljuc za kriptovanje
            XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_OAEP);

            // inicijalizacija za kriptovanje tajnog kljuca javnim RSA kljucem
            keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());

            // kreiranje EncryptedKey objekta koji sadrzi  enkriptovan tajni (session) kljuc
            EncryptedKey encryptedKey = keyCipher.encryptKey(doc, key);

            // u EncryptedData element koji se kriptuje kao KeyInfo stavljamo
            // kriptovan tajni kljuc
            // ovaj element je koreni element XML enkripcije
            EncryptedData encryptedData = xmlCipher.getEncryptedData();

            // kreira se KeyInfo element
            KeyInfo keyInfo = new KeyInfo(doc);

            // postavljamo naziv
            keyInfo.addKeyName("Kriptovani tajni kljuc");

            // postavljamo kriptovani kljuc
            keyInfo.add(encryptedKey);

            // postavljamo KeyInfo za element koji se kriptuje
            encryptedData.setKeyInfo(keyInfo);

            // trazi se element ciji sadrzaj se kriptuje
            NodeList cvs = doc.getElementsByTagName("cv");
            Element cv = (Element) cvs.item(0);

            xmlCipher.doFinal(doc, doc.getDocumentElement(), true); // kriptuje sa sadrzaj

            return doc;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}