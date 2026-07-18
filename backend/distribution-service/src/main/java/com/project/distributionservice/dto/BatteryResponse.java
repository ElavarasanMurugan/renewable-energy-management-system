package com.project.distributionservice.dto;

import lombok.Data;

@Data
public class BatteryResponse {
    // This is the battery response class
    private Long id;
    private String batteryName;
    private Double capacity;
    private String location;
    private String status;
    private Double currentCharge;
}
