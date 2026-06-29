package com.example.internengine.service;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.Internship;
import org.springframework.stereotype.Service;

@Service
public class EligibilityFilterService {

    public boolean isEligible(CandidateProfile candidate, Internship internship) {
        if (internship.getFilledCount() >= internship.getTotalCapacity()) {
            return false;
        }

        if (candidate.isPastIntern()) {
            return false;
        }

        if (!internship.isActive()) {
            return false;
        }

        return true;
    }
}
