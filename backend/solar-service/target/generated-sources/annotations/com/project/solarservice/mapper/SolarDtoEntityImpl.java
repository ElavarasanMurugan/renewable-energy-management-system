package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.entity.Solar;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T22:34:08+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class SolarDtoEntityImpl implements SolarDtoEntity {

    @Override
    public Solar dtoToSolar(SolarRequestDto solarRequestDto) {
        if ( solarRequestDto == null ) {
            return null;
        }

        Solar solar = new Solar();

        solar.setId( solarRequestDto.getId() );
        solar.setPanel_name( solarRequestDto.getPanel_name() );
        solar.setLocation( solarRequestDto.getLocation() );
        solar.setCapacity_kw( solarRequestDto.getCapacity_kw() );
        solar.setCurrent_generation( solarRequestDto.getCurrent_generation() );
        solar.setStatus( solarRequestDto.getStatus() );

        return solar;
    }

    @Override
    public void updateSolarFromDto(SolarRequestDto solarRequestDto, Solar solar) {
        if ( solarRequestDto == null ) {
            return;
        }

        if ( solarRequestDto.getId() != null ) {
            solar.setId( solarRequestDto.getId() );
        }
        if ( solarRequestDto.getPanel_name() != null ) {
            solar.setPanel_name( solarRequestDto.getPanel_name() );
        }
        if ( solarRequestDto.getLocation() != null ) {
            solar.setLocation( solarRequestDto.getLocation() );
        }
        if ( solarRequestDto.getCapacity_kw() != null ) {
            solar.setCapacity_kw( solarRequestDto.getCapacity_kw() );
        }
        if ( solarRequestDto.getCurrent_generation() != null ) {
            solar.setCurrent_generation( solarRequestDto.getCurrent_generation() );
        }
        if ( solarRequestDto.getStatus() != null ) {
            solar.setStatus( solarRequestDto.getStatus() );
        }
    }
}
