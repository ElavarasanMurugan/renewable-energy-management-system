package com.project.distributionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyReportResponse {

    private LocalDate date;
    private Double solarGeneration;
    private Double windGeneration;
    private Double totalRenewableGeneration;
    private Double batteryCharged;
    private Double batteryDischarged;
    private Double totalDemandServed;
}