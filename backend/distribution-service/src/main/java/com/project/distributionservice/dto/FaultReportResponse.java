package com.project.distributionservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class FaultReportResponse {

    private List<SolarFaultResponse> solarFaults;

    private List<WindFaultResponse> windFaults;

    private Integer totalFaults;
}