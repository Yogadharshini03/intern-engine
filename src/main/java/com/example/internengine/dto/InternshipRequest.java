package com.example.internengine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternshipRequest {
    private String companyName;
    private String roleTitle;
    private String requiredSkills;
    private String sector;
    private String location;
    private int totalCapacity;
    private int durationMonths;
}
