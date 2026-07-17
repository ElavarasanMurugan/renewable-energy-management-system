package com.project.solarservice.mapper;

import com.project.solarservice.dto.FaultResponse;
import com.project.solarservice.entity.Solar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SolarToFaultResponse {
    @Mapping(source = "id",target = "id")
    @Mapping(source = "panel_name",target = "deviceName")
    FaultResponse toFaultResponse(Solar solar);
}