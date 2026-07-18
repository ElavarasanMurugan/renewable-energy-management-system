package com.project.distributionservice.dto;

import lombok.Data;

@Data
public class SolarResponse {
    private Long id;
    private String panel_name;
    private String location;
    private Double capacity_kw;
    private Double current_generation;
    private String status;
}