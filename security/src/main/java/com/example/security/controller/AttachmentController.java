package com.example.security.controller;

import com.example.security.crypto.AsymmetricKeyDecryption;
import com.example.security.crypto.AsymmetricKeyEncryption;
import com.example.security.model.Attachment;
import com.example.security.model.AttachmentData;
import com.example.security.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class AttachmentController {
    private AttachmentService attachmentService;
    private AsymmetricKeyEncryption asymmetricKeyEncryption;
    private AsymmetricKeyDecryption asymmetricKeyDecryption;

    Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    public AttachmentController(AttachmentService attachmentService, AsymmetricKeyDecryption asymmetricKeyDecryption, AsymmetricKeyEncryption asymmetricKeyEncryption) {
        this.attachmentService = attachmentService;
        this.asymmetricKeyDecryption = asymmetricKeyDecryption;
        this.asymmetricKeyEncryption = asymmetricKeyEncryption;
    }

    @GetMapping("/download/file")
    public ResponseEntity<Resource> downloadFileByPath(@RequestParam("path") String path) throws Exception {
        logger.info("Downloading file with path: {}", path);
        Attachment attachment = null;
        attachment = attachmentService.getAttachmentByPath(path);

        if (attachment == null) {
            logger.error("File not found with path: {}", path);
            throw new Exception("File not found with path: " + path);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }


    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptDocument(@RequestBody String filePath) {
        logger.info("Encrypting document: {}", filePath);
        asymmetricKeyEncryption.testIt(filePath);
        logger.info("Encryption completed");
        return ResponseEntity.status(HttpStatus.OK).body("Encryption completed.");
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptDocument(@RequestBody String filePath) {
        logger.info("Decrypting document: {}", filePath);
        asymmetricKeyDecryption.testIt(filePath);
        logger.info("Decryption completed");
        return ResponseEntity.status(HttpStatus.OK).body("decryption completed");
    }

    @PostMapping("/upload")
    public AttachmentData uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("appUserId") Long appUserId) throws Exception {
        logger.info("Uploading file for appUserId: {}", appUserId);
        Attachment attachment = null;
        String downloadURL = "";
        try {
            String filePath = file.getOriginalFilename(); // Dobijanje putanje selektovanog fajla
            asymmetricKeyEncryption.testIt(filePath); // Enkripcija fajla

            attachment = attachmentService.saveAttachment(file, appUserId);
            downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(String.valueOf(attachment.getId()))
                    .toUriString();
            logger.info("File uploaded successfully. Download URL: {}", downloadURL);
        } catch (Exception e) {
            logger.error("Error uploading file for appUserId: {}", appUserId, e);
            throw new Exception("Could not save File: " + file.getOriginalFilename());
        }

        return new AttachmentData(attachment.getFileName(),
                downloadURL,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws Exception {
        logger.info("Downloading file by fileId");
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }

    @GetMapping("/download/appUser/{appUserId}")
    public ResponseEntity<Resource> downloadFileByAppUserId(@PathVariable Long appUserId) throws Exception {
        logger.info("Downloading file by appUserId");
        Attachment attachment = null;
        attachment = attachmentService.getAttachmentByAppUserId(appUserId);

        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }

    @GetMapping("/attachment/all")
    public List<Attachment> getAllAttachments() {
        logger.info("Getting all attachments");
        return attachmentService.getAllAttachments();
    }

}
