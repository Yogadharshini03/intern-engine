package com.example.internengine.controller;

import com.example.internengine.dto.CandidateProfileRequest;
import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.User;
import com.example.internengine.repository.CandidateProfileRepository;
import com.example.internengine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateProfileRepository profileRepository;
    private final UserRepository userRepository;

    @PostMapping("/profile")
    public ResponseEntity<?> saveProfile(
            @RequestBody CandidateProfileRequest request,
            @AuthenticationPrincipal String email
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CandidateProfile profile = profileRepository.findByUserId(user.getId())
                .orElse(new CandidateProfile());

        profile.setUser(user);
        profile.setDegree(request.getDegree());
        profile.setStream(request.getStream());
        profile.setCgpa(request.getCgpa());
        profile.setSkills(request.getSkills());
        profile.setPreferredLocation(request.getPreferredLocation());
        profile.setPreferredSector(request.getPreferredSector());
        profile.setSocialCategory(CandidateProfile.SocialCategory.valueOf(request.getSocialCategory().toUpperCase()));
        profile.setDistrictType(CandidateProfile.DistrictType.valueOf(request.getDistrictType().toUpperCase()));
        profile.setPastIntern(request.isPastIntern());

        profileRepository.save(profile);
        return ResponseEntity.ok(Map.of("message", "Profile saved successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return profileRepository.findByUserId(user.getId())
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
