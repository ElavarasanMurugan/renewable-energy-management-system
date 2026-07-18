package com.project.windservice.mapper;

import com.project.windservice.dto.WindGenerationRequest;
import com.project.windservice.entity.WindGenerationHistory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-16T19:24:08+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class GenerationRequestToEntityImpl implements GenerationRequestToEntity {

    @Override
    public WindGenerationHistory toGenerationEntity(WindGenerationRequest request) {
        if ( request == null ) {
            return null;
        }

        WindGenerationHistory windGenerationHistory = new WindGenerationHistory();

        windGenerationHistory.setGeneratedPower( request.getGeneratedPower() );

        return windGenerationHistory;
    }
}
