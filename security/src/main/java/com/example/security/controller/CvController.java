package com.example.security.controller;

import com.example.security.crypto.AsymmetricKeyDecryption;
import com.example.security.crypto.AsymmetricKeyEncryption;
import com.example.security.model.Cv;
import com.example.security.service.CvService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class CvController {
    private CvService cvService;
    private AsymmetricKeyEncryption asymmetricKeyEncryption;
    private AsymmetricKeyDecryption asymmetricKeyDecryption;

    public CvController(CvService cvService, AsymmetricKeyEncryption asymmetricKeyEncryption, AsymmetricKeyDecryption asymmetricKeyDecryption) {
        this.cvService = cvService;
        this.asymmetricKeyEncryption = asymmetricKeyEncryption;
        this.asymmetricKeyDecryption = asymmetricKeyDecryption;
    }


    @PostMapping("/uploadCv")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("appUserId") Long appUserId) throws Exception {
        Cv cv = null;
        String downloadURL = "";
        try {
            String filePath = file.getOriginalFilename(); // Dobijanje putanje selektovanog fajla
            asymmetricKeyEncryption.testIt(filePath); // Enkripcija fajla

            cv = cvService.saveCv(file, appUserId);
            downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(String.valueOf(cv.getId()))
                    .toUriString();
        } catch (Exception e) {
            throw new Exception("Could not save File: " + file.getOriginalFilename());
        }

        return downloadURL;
    }

    @GetMapping("/downloadCv/{appUserId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long appUserId) throws Exception {
        try {
            String filePath = cvService.getFilePathByAppUserId(appUserId);
            if (filePath == null) {
                throw new Exception("File not found for AppUser with ID: " + appUserId);
            }

            byte[] fileData = cvService.getFileData(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "cv.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);
        } catch (IOException e) {
            throw new Exception("Could not read file for AppUser with ID: " + appUserId);
        }
    }



}
