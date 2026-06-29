package com.example.internengine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "candidate_profiles")
@Getter
@Setter
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String degree;
    private String stream;

    @Column(precision = 4, scale = 2)
    private BigDecimal cgpa;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(name = "preferred_location")
    private String preferredLocation;

    @Column(name = "preferred_sector")
    private String preferredSector;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_category", length = 20)
    private SocialCategory socialCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "district_type", length = 20)
    private DistrictType districtType;

    @Column(name = "past_intern")
    private boolean pastIntern = false;

    public enum SocialCategory {
        GENERAL, OBC, SC, ST
    }

    public enum DistrictType {
        URBAN, RURAL, ASPIRATIONAL
    }
}