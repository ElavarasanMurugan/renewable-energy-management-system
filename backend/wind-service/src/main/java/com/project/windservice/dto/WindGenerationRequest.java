package com.project.windservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class WindGenerationRequest {
    @NotNull(message = "Generated power is required")
    @PositiveOrZero(message = "Generated power must be positive or zero")
    private Double generatedPower;
}
