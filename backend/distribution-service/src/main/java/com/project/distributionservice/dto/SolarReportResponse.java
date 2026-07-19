package com.project.distributionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolarReportResponse {
    private Long solarId;
    private LocalDate date;
    private Double totalGeneratedUnits;
}
