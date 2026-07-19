package com.project.distributionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WindReportResponse {
    private Long turbineId;
    private LocalDate date;
    private Double totalGeneratedPower;
}
