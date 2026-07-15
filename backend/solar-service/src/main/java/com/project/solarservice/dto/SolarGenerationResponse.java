package com.project.solarservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolarGenerationResponse {
    private Long id;
    private Long panelId;
    private String panel_name;
    private Double generated_units;
    private LocalDateTime generatedAt;
}
