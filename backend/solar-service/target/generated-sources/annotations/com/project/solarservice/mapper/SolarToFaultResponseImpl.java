package com.project.solarservice.mapper;

import com.project.solarservice.dto.FaultResponse;
import com.project.solarservice.entity.Solar;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-19T19:27:17+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class SolarToFaultResponseImpl implements SolarToFaultResponse {

    @Override
    public FaultResponse toFaultResponse(Solar solar) {
        if ( solar == null ) {
            return null;
        }

        FaultResponse faultResponse = new FaultResponse();

        faultResponse.setId( solar.getId() );
        faultResponse.setDeviceName( solar.getPanel_name() );

        return faultResponse;
    }
}
