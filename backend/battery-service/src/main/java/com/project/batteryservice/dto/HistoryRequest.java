package com.project.batteryservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class HistoryRequest {
    @NotNull(message = "Units is required")
    @PositiveOrZero(message = "Units must be positive or zero")
    private Double units;
}
