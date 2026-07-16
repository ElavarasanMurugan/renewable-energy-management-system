package com.project.windservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WindGenerationResponse {
    private Long id;
    private Long turbineId;
    private String deviceName;
    private String location;
    private Double capacity;
    private Double currentGeneration;
    private LocalDateTime generatedAt;
    private Double generatedPower;
}
