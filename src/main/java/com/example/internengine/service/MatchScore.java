package com.example.internengine.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchScore {
    private final double total;
    private final double skillScore;
    private final double sectorScore;
    private final double locationScore;
    private final double academicScore;
    private final double affirmativeScore;
}
