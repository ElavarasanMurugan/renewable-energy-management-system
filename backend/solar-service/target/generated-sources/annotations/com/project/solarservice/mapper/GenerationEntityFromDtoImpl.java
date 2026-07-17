package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarGenerationRequestDto;
import com.project.solarservice.entity.SolarGenerationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-15T22:46:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class GenerationEntityFromDtoImpl implements GenerationEntityFromDto {

    @Override
    public SolarGenerationEntity entityFromDto(SolarGenerationRequestDto solarGenerationRequestDto) {
        if ( solarGenerationRequestDto == null ) {
            return null;
        }

        SolarGenerationEntity solarGenerationEntity = new SolarGenerationEntity();

        solarGenerationEntity.setGenerated_units( solarGenerationRequestDto.getGenerated_units() );

        return solarGenerationEntity;
    }
}
