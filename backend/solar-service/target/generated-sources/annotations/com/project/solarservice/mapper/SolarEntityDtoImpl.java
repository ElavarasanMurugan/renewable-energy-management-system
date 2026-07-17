package com.project.solarservice.mapper;

import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T22:34:08+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class SolarEntityDtoImpl implements SolarEntityDto {

    @Override
    public SolarResponseDto entityToDto(Solar solar) {
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
}
