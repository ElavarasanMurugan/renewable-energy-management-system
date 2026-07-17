package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarGenerationRequestDto;
import com.project.solarservice.entity.SolarGenerationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenerationEntityFromDto {
    SolarGenerationEntity entityFromDto(SolarGenerationRequestDto solarGenerationRequestDto);
}
