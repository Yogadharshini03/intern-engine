package com.example.internengine.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CandidateProfileRequest {
    private String degree;
    private String stream;
    private BigDecimal cgpa;
    private String skills;
    private String preferredLocation;
    private String preferredSector;
    private String socialCategory;
    private String districtType;
    private boolean pastIntern;
}
