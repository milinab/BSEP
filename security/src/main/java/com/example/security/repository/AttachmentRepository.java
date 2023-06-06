package com.example.security.repository;

import com.example.security.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByAppUser_Id(Long appUserId);
}
