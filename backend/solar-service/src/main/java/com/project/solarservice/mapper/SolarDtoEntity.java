package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.entity.Solar;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SolarDtoEntity {
    Solar dtoToSolar(SolarRequestDto solarRequestDto);
    void updateSolarFromDto(SolarRequestDto solarRequestDto, @MappingTarget Solar solar);
}
