package com.example.security.repository;

import com.example.security.model.AppUser;
import com.example.security.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByWorkerId(Long workerId);

    List<Work> findByProjectId(Long projectId);

    List<Work> findByProjectManager(AppUser projectManager);


}
