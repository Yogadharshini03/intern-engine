package com.example.internengine.controller;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.MatchResult;
import com.example.internengine.model.User;
import com.example.internengine.repository.CandidateProfileRepository;
import com.example.internengine.repository.MatchResultRepository;
import com.example.internengine.repository.UserRepository;
import com.example.internengine.service.MatchingOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchingOrchestrator matchingOrchestrator;
    private final MatchResultRepository matchResultRepository;
    private final CandidateProfileRepository profileRepository;
    private final UserRepository userRepository;

    @PostMapping("/run")
    public ResponseEntity<?> runMatching(@AuthenticationPrincipal String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CandidateProfile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Please complete your profile first"));

        List<MatchResult> results = matchingOrchestrator.runMatchingForCandidate(profile);

        return ResponseEntity.ok(Map.of(
                "message", "Matching complete",
                "totalMatches", results.size()
        ));
    }

    @GetMapping("/results")
    public ResponseEntity<List<MatchResult>> getResults(@AuthenticationPrincipal String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CandidateProfile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<MatchResult> results = matchResultRepository
                .findByCandidateIdOrderByMatchScoreDesc(profile.getId());

        return ResponseEntity.ok(results);
    }
}
