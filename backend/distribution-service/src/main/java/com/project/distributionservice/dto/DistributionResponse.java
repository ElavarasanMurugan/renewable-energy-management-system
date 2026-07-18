package com.project.distributionservice.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DistributionResponse {
    private Long id;
    private Double requiredUnits;
    private Double renewableUnits;
    private Double batteryUsed;
    private Double excessUnitsStored;
    private String status;
    private LocalDateTime distributedAt;
}
