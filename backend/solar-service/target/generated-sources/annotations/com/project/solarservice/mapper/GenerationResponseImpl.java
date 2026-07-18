package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarGenerationResponse;
import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T21:55:35+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class GenerationResponseImpl implements GenerationResponse {

    @Override
    public SolarResponseDto RequestToResponse(Solar solar) {
        if ( solar == null ) {
            return null;
        }

        SolarResponseDto solarResponseDto = new SolarResponseDto();

        solarResponseDto.setId( solar.getId() );
        solarResponseDto.setPanel_name( solar.getPanel_name() );
        solarResponseDto.setLocation( solar.getLocation() );
        solarResponseDto.setCapacity_kw( solar.getCapacity_kw() );
        solarResponseDto.setCurrent_generation( solar.getCurrent_generation() );
        solarResponseDto.setStatus( solar.getStatus() );

        return solarResponseDto;
    }

    @Override
    public SolarGenerationResponse GenerationRequestToResponse(SolarGenerationEntity solarGeneration) {
        if ( solarGeneration == null ) {
            return null;
        }

        SolarGenerationResponse solarGenerationResponse = new SolarGenerationResponse();

        solarGenerationResponse.setPanelId( solarGenerationSolarId( solarGeneration ) );
        solarGenerationResponse.setPanel_name( solarGenerationSolarPanel_name( solarGeneration ) );
        solarGenerationResponse.setId( solarGeneration.getId() );
        solarGenerationResponse.setGenerated_units( solarGeneration.getGenerated_units() );
        solarGenerationResponse.setGenerated_at( solarGeneration.getGenerated_at() );

        return solarGenerationResponse;
    }

    @Override
    public List<SolarGenerationResponse> GenrationRequestToResponseAll(List<SolarGenerationEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SolarGenerationResponse> list = new ArrayList<SolarGenerationResponse>( entities.size() );
        for ( SolarGenerationEntity solarGenerationEntity : entities ) {
            list.add( GenerationRequestToResponse( solarGenerationEntity ) );
        }

        return list;
    }

    private Long solarGenerationSolarId(SolarGenerationEntity solarGenerationEntity) {
        Solar solar = solarGenerationEntity.getSolar();
        if ( solar == null ) {
            return null;
        }
        return solar.getId();
    }

    private String solarGenerationSolarPanel_name(SolarGenerationEntity solarGenerationEntity) {
        Solar solar = solarGenerationEntity.getSolar();
        if ( solar == null ) {
            return null;
        }
        return solar.getPanel_name();
    }
}
