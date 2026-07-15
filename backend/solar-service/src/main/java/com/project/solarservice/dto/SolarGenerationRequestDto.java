package com.project.solarservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SolarGenerationRequestDto {
    @NotNull(message = "Generated units is required")
    @Positive(message = "Generated units must be positive")
    private Double generated_units;
}
