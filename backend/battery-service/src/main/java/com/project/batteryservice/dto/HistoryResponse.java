package com.project.batteryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryResponse {
    private Long id;
    private Long batteryId;
    private String batteryName;
    private Double capacity;
    private Double currentCharge;
    private Double units;
    private String status;
    private String alertMessage;
}
