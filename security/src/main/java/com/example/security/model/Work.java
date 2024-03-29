package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private AppUser worker;

    @ManyToOne
    @JoinColumn(name = "project_manager")
    private AppUser projectManager;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private int duration;
    private String description;
    private LocalDate startTime;
    private LocalDate endTime;
}
