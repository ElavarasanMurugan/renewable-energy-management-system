package com.project.solarservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SolarRequestDto {
    private Long id;

    @NotBlank(message = "Device name is required")
    private String panel_name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Double capacity_kw;

    @NotNull(message = "Current generation is required")
    @PositiveOrZero(message = "Current must be positive" )
    private Double current_generation;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "(?i)ACTIVE|INACTIVE|MAINTENANCE",
            message = "Status must be either ACTIVE,INACTIVE,MAINTENANCE"
    )
    private String status;
}
