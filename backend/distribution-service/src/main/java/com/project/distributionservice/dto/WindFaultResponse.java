package com.project.distributionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WindFaultResponse {
    private Long id;
    private String deviceName;
    private String message;
    private boolean fault;
}