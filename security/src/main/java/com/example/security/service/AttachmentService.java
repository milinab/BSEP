package com.example.security.service;

import com.example.security.model.AppUser;
import com.example.security.model.Attachment;
import com.example.security.repository.AppUserRepository;
import com.example.security.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AttachmentService {

    private AttachmentRepository attachmentRepository;
    private AppUserRepository appUserRepository;
    public AttachmentService(AttachmentRepository attachmentRepository, AppUserRepository appUserRepository){
        this.attachmentRepository = attachmentRepository;
        this.appUserRepository = appUserRepository;
    }

    public Attachment saveAttachment(MultipartFile file, Long appUserId) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            long attachmentId = generateAttachmentId(); // Generisanje ID-a za Attachment

            String filePath = "C:/Users/Nemanja/Desktop/bsep/BSEP/security/src/main/java/com/example/security/data/enc_" + file.getOriginalFilename();

            Attachment attachment = new Attachment("enc_" + fileName, file.getContentType(), file.getBytes(), filePath);

            attachment.setId(attachmentId); // Pridruživanje ID-a objektu Attachment

            AppUser appUser = appUserRepository.findById(appUserId)
                    .orElseThrow(() -> new Exception("AppUser not found with Id: " + appUserId));

            attachment.setAppUser(appUser);

            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Transactional
    public Attachment getAttachmentByPath(String path) {
        return attachmentRepository.findByPath(path);
    }

    private long generateAttachmentId() {
        return System.currentTimeMillis();
    }


    public Attachment getAttachment(Long fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
    @Transactional
    public Attachment getAttachmentByAppUserId(Long appUserId) throws Exception {
        return attachmentRepository.findByAppUser_Id(appUserId)
                .orElseThrow(() -> new Exception("File not found for App User ID: " + appUserId));
    }

    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }


}
