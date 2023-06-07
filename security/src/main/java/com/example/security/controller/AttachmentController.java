package com.example.security.controller;

import com.example.security.crypto.AsymmetricKeyDecryption;
import com.example.security.crypto.AsymmetricKeyEncryption;
import com.example.security.model.Attachment;
import com.example.security.model.AttachmentData;
import com.example.security.service.AttachmentService;
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

    public AttachmentController(AttachmentService attachmentService, AsymmetricKeyDecryption asymmetricKeyDecryption, AsymmetricKeyEncryption asymmetricKeyEncryption) {
        this.attachmentService = attachmentService;
        this.asymmetricKeyDecryption = asymmetricKeyDecryption;
        this.asymmetricKeyEncryption = asymmetricKeyEncryption;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptDocument(@RequestBody String filePath) {
        asymmetricKeyEncryption.testIt(filePath);
        return ResponseEntity.status(HttpStatus.OK).body("Encryption completed.");
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptDocument(@RequestBody String filePath) {
        asymmetricKeyDecryption.testIt(filePath);
        return ResponseEntity.status(HttpStatus.OK).body("decryption completed");
    }

    @PostMapping("/upload")
    public AttachmentData uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = null;
        String downloadURl = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(String.valueOf(attachment.getId()))
                .toUriString();

        return new AttachmentData(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws Exception {
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
        return attachmentService.getAllAttachments();
    }

}
