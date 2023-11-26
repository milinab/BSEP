package com.example.security.controller;

import com.example.security.crypto.AsymmetricKeyDecryption;
import com.example.security.crypto.AsymmetricKeyEncryption;
import com.example.security.model.AppUser;
import com.example.security.model.Cv;
import com.example.security.service.CvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class CvController {
    private CvService cvService;
    Logger logger = LoggerFactory.getLogger(CvController.class);
    private AsymmetricKeyEncryption asymmetricKeyEncryption;
    private AsymmetricKeyDecryption asymmetricKeyDecryption;

    public CvController(CvService cvService, AsymmetricKeyEncryption asymmetricKeyEncryption, AsymmetricKeyDecryption asymmetricKeyDecryption) {
        this.cvService = cvService;
        this.asymmetricKeyEncryption = asymmetricKeyEncryption;
        this.asymmetricKeyDecryption = asymmetricKeyDecryption;
    }


    @PostMapping("/uploadCv")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("appUserId") Long appUserId) throws Exception {
        logger.info("Uploading CV for appUserId: {}", appUserId);
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
            logger.info("CV uploaded successfully. Download URL: {}", downloadURL);
        } catch (Exception e) {
            logger.error("Error uploading CV for appUserId: {}", appUserId);
            throw new Exception("Could not save File: " + file.getOriginalFilename());
        }

        return downloadURL;
    }

    @GetMapping("/downloadCv/{appUserId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long appUserId) throws Exception {
        logger.info("Downloading CV for appUserId: {}", appUserId);
        try {
            String filePath = cvService.getFilePathByAppUserId(appUserId);
            if (filePath == null) {
                logger.warn("Failed to download file for user with ID: {}, reason: file not found.", appUserId);
                throw new Exception("File not found for AppUser with ID: " + appUserId);
            }

            byte[] fileData = cvService.getFileData(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "cv.docx");
            logger.info("CV for user with id: {} downloaded successfully.", appUserId);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);
        } catch (IOException e) {
            logger.warn("Error downloading CV for appUserId: {}", appUserId);
            throw new Exception("Could not read file for AppUser with ID: " + appUserId);
        }
    }

    @GetMapping("/cvs")
    public List<Cv> getAllCvs() {
        return cvService.getAllCvs();
    }

    @GetMapping("/projectManager/{id}")
    public ResponseEntity<List<Cv>> getCvsByProjectManager(@PathVariable("id") Long projectManagerId) {
        AppUser projectManager = new AppUser();
        projectManager.setId(projectManagerId);

        List<Cv> cvs = cvService.getCvsByProjectManager(projectManager);
        return ResponseEntity.ok(cvs);
    }

}
