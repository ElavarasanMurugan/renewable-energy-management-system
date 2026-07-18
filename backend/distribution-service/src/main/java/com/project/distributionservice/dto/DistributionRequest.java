package com.project.distributionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DistributionRequest {
    @NotNull(message = "The units is required")
    @PositiveOrZero(message = "The units must be either positive or zero")
    private Double requiredUnits;
}
