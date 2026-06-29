package com.example.internengine.service;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.Internship;
import org.springframework.stereotype.Service;

@Service
public class AIExplanationService {

    public String generateExplanation(CandidateProfile c, Internship i, MatchScore score) {
        return String.format(
                "This is a %.0f%% match: your skills and preferences align well with the %s role at %s.",
                score.getTotal(), i.getRoleTitle(), i.getCompanyName()
        );
    }
}
