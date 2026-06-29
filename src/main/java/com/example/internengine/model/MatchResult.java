package com.example.internengine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_results")
@Getter
@Setter
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateProfile candidate;

    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    @Column(name = "match_score", precision = 5, scale = 2)
    private BigDecimal matchScore;

    @Column(name = "skill_score", precision = 5, scale = 2)
    private BigDecimal skillScore;

    @Column(name = "sector_score", precision = 5, scale = 2)
    private BigDecimal sectorScore;

    @Column(name = "location_score", precision = 5, scale = 2)
    private BigDecimal locationScore;

    @Column(name = "academic_score", precision = 5, scale = 2)
    private BigDecimal academicScore;

    @Column(name = "affirmative_score", precision = 5, scale = 2)
    private BigDecimal affirmativeScore;

    @Column(name = "ai_explanation", columnDefinition = "TEXT")
    private String aiExplanation;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Status status = Status.MATCHED;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        MATCHED, OFFERED, ACCEPTED, REJECTED
    }
}
