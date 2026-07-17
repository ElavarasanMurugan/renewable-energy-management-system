package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolarEntityDto {
    SolarResponseDto entityToDto(Solar solar);
}
