package com.project.solarservice.dto;

import lombok.Data;

@Data
public class SolarResponseDto {
    private Long id;
    private String panel_name;
    private Double capacity_kw;
    private String status;
}
