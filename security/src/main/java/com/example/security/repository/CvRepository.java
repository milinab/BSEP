package com.example.security.repository;

import com.example.security.model.AppUser;
import com.example.security.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    Cv findByAppUser(AppUser appUser);
    @Query("SELECT c FROM Cv c JOIN FETCH c.appUser")
    List<Cv> findAllWithAppUser();
    List<Cv> findAllByAppUser(AppUser appUser);

}
