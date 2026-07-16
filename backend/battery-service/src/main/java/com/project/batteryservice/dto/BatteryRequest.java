package com.project.batteryservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BatteryRequest {
    @NotBlank(message = "Battery name is required")
    private String batteryName;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Double capacity;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "(?i)ACTIVE|INACTIVE|MAINTENANCE",
            message = "Status must be either active or inactive or maintenance"
    )
    private String status;


}
