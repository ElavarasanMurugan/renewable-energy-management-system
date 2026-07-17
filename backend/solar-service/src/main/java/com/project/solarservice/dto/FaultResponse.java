package com.project.solarservice.dto;

import lombok.Data;

@Data
public class FaultResponse {
    private Long id;
    private String deviceName;
    private String message;
    private boolean fault;
}