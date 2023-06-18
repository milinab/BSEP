package com.example.security.service;

import com.example.security.crypto.AsymmetricKeyDecryption;
import com.example.security.model.AppUser;
import com.example.security.model.Cv;
import com.example.security.model.Work;
import com.example.security.repository.AppUserRepository;
import com.example.security.repository.CvRepository;
import com.example.security.repository.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CvService {
    private CvRepository  cvRepository;
    private AppUserRepository appUserRepository;
    private WorkRepository workRepository;
    private AsymmetricKeyDecryption asymmetricKeyDecryption;
    public CvService(CvRepository cvRepository, AppUserRepository appUserRepository, WorkRepository workRepository, AsymmetricKeyDecryption asymmetricKeyDecryption){
        this.appUserRepository = appUserRepository;
        this.cvRepository = cvRepository;
        this.workRepository = workRepository;
        this.asymmetricKeyDecryption = asymmetricKeyDecryption;
    }

    public Cv saveCv(MultipartFile file, Long appUserId) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence: " + fileName);
            }

            long attachmentId = generateAttachmentId(); // Generisanje ID-a za Attachment

            String relativePath = "../data";
            Path currentWorkingDirectory = Paths.get("").toAbsolutePath();
            Path absolutePath = currentWorkingDirectory.resolve(relativePath);

            System.out.println("Apsolutna putanja: " + absolutePath.toString());

            String filePath = "C:/Users/Nemanja/Desktop/bsep/BSEP/security/src/main/java/com/example/security/data/enc_" + file.getOriginalFilename();

            Cv cv = new Cv(filePath);

            cv.setId(attachmentId); // Pridruživanje ID-a objektu Attachment

            AppUser appUser = appUserRepository.findById(appUserId)
                    .orElseThrow(() -> new Exception("AppUser not found with Id: " + appUserId));

            cv.setAppUser(appUser);

            return cvRepository.save(cv);
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }

    }
    private long generateAttachmentId() {
        return System.currentTimeMillis();
    }

    public String getFilePathByAppUserId(Long appUserId) throws Exception {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new Exception("AppUser not found with Id: " + appUserId));

        Cv cv = cvRepository.findByAppUser(appUser);
        if (cv == null) {
            throw new Exception("CV not found for AppUser with Id: " + appUserId);
        }

        String encryptedFilePath = cv.getPath();
        Path path = Paths.get(encryptedFilePath);
        Path parentPath = path.getParent();
        String decryptedFilePath =parentPath +  "/dec_" + new File(encryptedFilePath).getName();

        // Dekriptovanje fajla
        asymmetricKeyDecryption.testIt(encryptedFilePath);

        return decryptedFilePath;
    }
    public List<Cv> getAllCvs() {
        return cvRepository.findAllWithAppUser();
    }

    public byte[] getFileData(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public List<Cv> getCvsByProjectManager(AppUser projectManager) {
        List<Cv> cvs = new ArrayList<>();

        List<Work> works = workRepository.findByProjectManager(projectManager);
        for (Work work : works) {
            AppUser worker = work.getWorker();
            List<Cv> workerCvs = cvRepository.findAllByAppUser(worker);
            cvs.addAll(workerCvs);
        }

        return cvs;
    }

}
