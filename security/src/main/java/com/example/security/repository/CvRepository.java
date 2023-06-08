package com.example.security.repository;

import com.example.security.model.AppUser;
import com.example.security.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    Cv findByAppUser(AppUser appUser);
}
