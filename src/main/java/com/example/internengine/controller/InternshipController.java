package com.example.internengine.controller;

import com.example.internengine.dto.InternshipRequest;
import com.example.internengine.model.Internship;
import com.example.internengine.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipRepository internshipRepository;

    @GetMapping
    public ResponseEntity<List<Internship>> getAllInternships() {
        return ResponseEntity.ok(internshipRepository.findByActiveTrue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInternshipById(@PathVariable Long id) {
        return internshipRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addInternship(@RequestBody InternshipRequest request) {
        Internship internship = new Internship();
        internship.setCompanyName(request.getCompanyName());
        internship.setRoleTitle(request.getRoleTitle());
        internship.setRequiredSkills(request.getRequiredSkills());
        internship.setSector(request.getSector());
        internship.setLocation(request.getLocation());
        internship.setTotalCapacity(request.getTotalCapacity());
        internship.setDurationMonths(request.getDurationMonths());
        internship.setActive(true);

        internshipRepository.save(internship);
        return ResponseEntity.ok(Map.of("message", "Internship posted successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateInternship(@PathVariable Long id) {
        Internship internship = internshipRepository.findById(id).orElse(null);
        if (internship == null) {
            return ResponseEntity.notFound().build();
        }
        internship.setActive(false);
        internshipRepository.save(internship);
        return ResponseEntity.ok(Map.of("message", "Internship deactivated"));
    }
}
