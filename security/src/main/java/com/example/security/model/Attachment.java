package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment {
    @Id
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;

    private String path;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = true)
    private AppUser appUser;
    public Attachment( String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public Attachment( String fileName, String fileType, byte[] data, String path) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.path = path;
    }
    public Attachment(String fileName, String fileType, byte[] data, AppUser appUser) {
        this.appUser = appUser;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
    public Attachment(String fileName, String fileType, byte[] data, AppUser appUser, String path) {
        this.appUser = appUser;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.path = path;
    }
}
