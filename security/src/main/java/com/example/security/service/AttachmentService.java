package com.example.security.service;

import com.example.security.model.Attachment;
import com.example.security.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AttachmentService {

    private AttachmentRepository attachmentRepository;
    public AttachmentService(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());
            return attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
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
