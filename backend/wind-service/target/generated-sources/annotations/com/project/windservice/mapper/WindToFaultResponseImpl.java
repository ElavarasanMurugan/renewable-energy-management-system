package com.project.windservice.mapper;

import com.project.windservice.dto.FaultResponse;
import com.project.windservice.entity.WindTurbine;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T23:01:41+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class WindToFaultResponseImpl implements WindToFaultResponse {

    @Override
    public FaultResponse toFaultResponse(WindTurbine windTurbine) {
        if ( windTurbine == null ) {
            return null;
        }

        FaultResponse faultResponse = new FaultResponse();

        faultResponse.setId( windTurbine.getId() );
        faultResponse.setDeviceName( windTurbine.getDeviceName() );

        return faultResponse;
    }
}
