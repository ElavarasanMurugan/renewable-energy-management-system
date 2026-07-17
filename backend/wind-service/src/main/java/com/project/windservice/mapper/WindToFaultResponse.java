package com.project.windservice.mapper;

import com.project.windservice.dto.FaultResponse;

import com.project.windservice.entity.WindTurbine;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface WindToFaultResponse {
    FaultResponse toFaultResponse(WindTurbine windTurbine);
}