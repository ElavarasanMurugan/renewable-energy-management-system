package com.project.batteryservice.dto;

import lombok.Data;

@Data
public class PercentageResponse {
    private Long batteryId;
    private Double percentage;
}
