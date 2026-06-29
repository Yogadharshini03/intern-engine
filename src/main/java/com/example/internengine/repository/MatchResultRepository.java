package com.example.internengine.repository;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
    List<MatchResult> findByCandidateIdOrderByMatchScoreDesc(Long candidateId);
    void deleteByCandidateId(Long candidateId);
    List<MatchResult> findAllByOrderByMatchScoreDesc();
    long countByCandidateSocialCategory(CandidateProfile.SocialCategory socialCategory);
    long countByCandidateDistrictType(CandidateProfile.DistrictType districtType);
}
