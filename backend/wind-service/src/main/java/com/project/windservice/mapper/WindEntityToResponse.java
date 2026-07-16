package com.project.windservice.mapper;

import com.project.windservice.dto.WindTurbineResponse;
import com.project.windservice.entity.WindTurbine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WindEntityToResponse {
    WindTurbineResponse toWindTurbineResponse(WindTurbine windTurbine);
    List<WindTurbineResponse> listToWindTurbineResponse(List<WindTurbine> windTurbines);
}
