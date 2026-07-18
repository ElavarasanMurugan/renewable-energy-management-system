package com.project.distributionservice.dto;

import lombok.Data;

@Data
public class BatteryStatusResponse {
    private Long batteryId;
    private String status;
}