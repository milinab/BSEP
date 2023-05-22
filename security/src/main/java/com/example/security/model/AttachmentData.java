package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentData {

    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}