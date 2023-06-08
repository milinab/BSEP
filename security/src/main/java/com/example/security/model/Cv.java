package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cv {
    @Id
    private Long id;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = true)
    private AppUser appUser;

    public Cv(String filePath) {
        this.path = filePath;
    }
}
