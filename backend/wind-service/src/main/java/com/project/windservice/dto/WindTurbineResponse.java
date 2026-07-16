package com.project.windservice.dto;

import lombok.Data;

@Data
public class WindTurbineResponse {
    private Long id;
    private String deviceName;
    private String location;
    private Double capacity;
    private Double currentGeneration;
    private String status;
}
