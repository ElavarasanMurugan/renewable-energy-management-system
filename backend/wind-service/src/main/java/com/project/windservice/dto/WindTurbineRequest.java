package com.project.windservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WindTurbineRequest {
    @NotBlank(message = "Device name is required")
    private String deviceName;

    @NotBlank(message =  "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Double capacity;

    @NotNull(message = "Current Generation is required")
    @PositiveOrZero(message = "Current Generation must be positive")
    private Double currentGeneration;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "(?i)ACTIVE|INACTIVE|MAINTENANCE",
            message = "Status must be either active or inactive or maintenance"
    )
    private String status;
}
