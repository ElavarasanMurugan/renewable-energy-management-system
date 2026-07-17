package com.project.windservice.mapper;

import com.project.windservice.dto.WindGenerationRequest;
import com.project.windservice.dto.WindGenerationResponse;
import com.project.windservice.entity.WindGenerationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenerationEntityToResponse {
    @Mapping(source = "windTurbine.id",target = "turbineId")
    @Mapping(source = "windTurbine.deviceName",target = "deviceName")
    @Mapping(source = "windTurbine.location",target = "location")
    @Mapping(source = "windTurbine.currentGeneration",target = "currentGeneration")
    @Mapping(source = "windTurbine.capacity",target = "capacity")
    WindGenerationResponse toGenerationResponse(WindGenerationHistory history);

    List<WindGenerationResponse> toListGeneration(List<WindGenerationHistory> windGenerationHistories);
}
