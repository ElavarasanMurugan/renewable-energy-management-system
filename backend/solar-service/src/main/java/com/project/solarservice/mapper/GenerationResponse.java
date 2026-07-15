package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarGenerationResponse;
import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenerationResponse {
    SolarResponseDto RequestToResponse(Solar solar);
    @Mapping(source="solarGeneration.solar.id",target="panelId")
    @Mapping(source = "solarGeneration.solar.panel_name",target="panel_name")
    SolarGenerationResponse GenerationRequestToResponse(SolarGenerationEntity solarGeneration);

    List<SolarGenerationResponse> GenrationRequestToResponseAll(List<SolarGenerationEntity> entities);
}
