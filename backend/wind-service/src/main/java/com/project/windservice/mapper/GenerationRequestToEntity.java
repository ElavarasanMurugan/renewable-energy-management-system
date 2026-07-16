package com.project.windservice.mapper;

import com.project.windservice.dto.WindGenerationRequest;
import com.project.windservice.entity.WindGenerationHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerationRequestToEntity {
    WindGenerationHistory toGenerationEntity(WindGenerationRequest request);
}
