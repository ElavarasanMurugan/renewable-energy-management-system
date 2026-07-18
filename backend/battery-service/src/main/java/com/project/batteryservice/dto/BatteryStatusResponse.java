package com.project.batteryservice.dto;

import lombok.Data;

@Data
public class BatteryStatusResponse {
    private Long batteryId;
    private String status;
}
