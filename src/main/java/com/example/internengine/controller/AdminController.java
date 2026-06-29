package com.example.internengine.controller;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.MatchResult;
import com.example.internengine.repository.CandidateProfileRepository;
import com.example.internengine.repository.InternshipRepository;
import com.example.internengine.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final MatchResultRepository matchResultRepository;
    private final CandidateProfileRepository profileRepository;
    private final InternshipRepository internshipRepository;

    @GetMapping("/matches")
    public ResponseEntity<List<MatchResult>> getAllMatches() {
        return ResponseEntity.ok(matchResultRepository.findAllByOrderByMatchScoreDesc());
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalCandidates = profileRepository.count();
        long totalInternships = internshipRepository.countByActiveTrue();
        long totalMatches = matchResultRepository.count();

        Map<String, Long> categoryStats = Map.of(
                "GENERAL", matchResultRepository.countByCandidateSocialCategory(CandidateProfile.SocialCategory.GENERAL),
                "OBC", matchResultRepository.countByCandidateSocialCategory(CandidateProfile.SocialCategory.OBC),
                "SC", matchResultRepository.countByCandidateSocialCategory(CandidateProfile.SocialCategory.SC),
                "ST", matchResultRepository.countByCandidateSocialCategory(CandidateProfile.SocialCategory.ST)
        );

        Map<String, Long> districtStats = Map.of(
                "URBAN", matchResultRepository.countByCandidateDistrictType(CandidateProfile.DistrictType.URBAN),
                "RURAL", matchResultRepository.countByCandidateDistrictType(CandidateProfile.DistrictType.RURAL),
                "ASPIRATIONAL", matchResultRepository.countByCandidateDistrictType(CandidateProfile.DistrictType.ASPIRATIONAL)
        );

        return ResponseEntity.ok(Map.of(
                "totalCandidates", totalCandidates,
                "totalInternships", totalInternships,
                "totalMatches", totalMatches,
                "categoryBreakdown", categoryStats,
                "districtBreakdown", districtStats
        ));
    }

    @PatchMapping("/matches/{id}/status")
    public ResponseEntity<?> updateMatchStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        MatchResult result = matchResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        result.setStatus(MatchResult.Status.valueOf(body.get("status").toUpperCase()));
        matchResultRepository.save(result);

        return ResponseEntity.ok(Map.of("message", "Status updated to " + result.getStatus()));
    }
}
