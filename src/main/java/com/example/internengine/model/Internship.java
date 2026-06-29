package com.example.internengine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "internships")
@Getter
@Setter
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "role_title", nullable = false, length = 150)
    private String roleTitle;

    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    private String sector;
    private String location;

    @Column(name = "total_capacity", nullable = false)
    private int totalCapacity;

    @Column(name = "filled_count")
    private int filledCount = 0;

    @Column(name = "duration_months")
    private int durationMonths;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
