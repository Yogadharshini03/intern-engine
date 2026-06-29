package com.example.internengine.service;

import com.example.internengine.model.CandidateProfile;
import com.example.internengine.model.Internship;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ScoringService {

    private static final double W_SKILL = 0.30;
    private static final double W_SECTOR = 0.20;
    private static final double W_LOCATION = 0.15;
    private static final double W_ACADEMIC = 0.15;
    private static final double W_AFFIRMATIVE = 0.15;
    private static final double W_PAST = 0.05;

    public MatchScore calculate(CandidateProfile c, Internship i) {
        double skillScore = calculateSkillMatch(c.getSkills(), i.getRequiredSkills());
        double sectorScore = c.getPreferredSector().equalsIgnoreCase(i.getSector()) ? 10.0 : 0.0;
        double locationScore = c.getPreferredLocation().equalsIgnoreCase(i.getLocation()) ? 10.0 : 0.0;
        double academicScore = (c.getCgpa().doubleValue() / 10.0) * 10.0;
        double affirmativeScore = calculateAffirmativeScore(c);
        double pastScore = c.isPastIntern() ? 0.0 : 10.0;

        double total = (skillScore * W_SKILL)
                + (sectorScore * W_SECTOR)
                + (locationScore * W_LOCATION)
                + (academicScore * W_ACADEMIC)
                + (affirmativeScore * W_AFFIRMATIVE)
                + (pastScore * W_PAST);

        return new MatchScore(total * 10, skillScore, sectorScore, locationScore, academicScore, affirmativeScore);
    }

    private double calculateSkillMatch(String candidateSkills, String requiredSkills) {
        List<String> cSkills = Arrays.asList(candidateSkills.toLowerCase().split(","));
        List<String> rSkills = Arrays.asList(requiredSkills.toLowerCase().split(","));
        long matched = rSkills.stream().filter(cSkills::contains).count();
        return rSkills.isEmpty() ? 0 : ((double) matched / rSkills.size()) * 10.0;
    }

    private double calculateAffirmativeScore(CandidateProfile c) {
        double score = 0;
        if (c.getDistrictType() == CandidateProfile.DistrictType.RURAL
                || c.getDistrictType() == CandidateProfile.DistrictType.ASPIRATIONAL) {
            score += 5;
        }
        if (c.getSocialCategory() == CandidateProfile.SocialCategory.SC
                || c.getSocialCategory() == CandidateProfile.SocialCategory.ST) {
            score += 5;
        } else if (c.getSocialCategory() == CandidateProfile.SocialCategory.OBC) {
            score += 3;
        }
        return score;
    }
}
