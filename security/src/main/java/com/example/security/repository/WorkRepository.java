package com.example.security.repository;

import com.example.security.model.AppUser;
import com.example.security.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
}
