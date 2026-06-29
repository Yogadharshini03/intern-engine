package com.example.internengine.service;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.Internship;
import com.example.internengine.model.MatchResult;
import com.example.internengine.repository.InternshipRepository;
import com.example.internengine.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingOrchestrator {

    private final EligibilityFilterService eligibilityFilter;
    private final ScoringService scoringService;
    private final AIExplanationService aiExplanationService;
    private final MatchResultRepository matchResultRepository;
    private final InternshipRepository internshipRepository;

    public List<MatchResult> runMatchingForCandidate(CandidateProfile candidate) {
        matchResultRepository.deleteByCandidateId(candidate.getId());

        List<Internship> internships = internshipRepository.findByActiveTrue();
        List<MatchResult> results = new ArrayList<>();

        for (Internship internship : internships) {
            if (!eligibilityFilter.isEligible(candidate, internship)) {
                continue;
            }

            MatchScore score = scoringService.calculate(candidate, internship);
            String explanation = aiExplanationService.generateExplanation(candidate, internship, score);

            MatchResult result = new MatchResult();
            result.setCandidate(candidate);
            result.setInternship(internship);
            result.setMatchScore(BigDecimal.valueOf(score.getTotal()));
            result.setSkillScore(BigDecimal.valueOf(score.getSkillScore()));
            result.setSectorScore(BigDecimal.valueOf(score.getSectorScore()));
            result.setLocationScore(BigDecimal.valueOf(score.getLocationScore()));
            result.setAcademicScore(BigDecimal.valueOf(score.getAcademicScore()));
            result.setAffirmativeScore(BigDecimal.valueOf(score.getAffirmativeScore()));
            result.setAiExplanation(explanation);

            results.add(result);
        }

        results.sort((a, b) -> b.getMatchScore().compareTo(a.getMatchScore()));
        return matchResultRepository.saveAll(results);
    }
}
