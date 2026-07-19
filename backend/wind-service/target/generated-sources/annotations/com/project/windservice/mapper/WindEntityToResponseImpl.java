package com.project.windservice.mapper;

import com.project.windservice.dto.WindTurbineResponse;
import com.project.windservice.entity.WindTurbine;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-19T19:27:30+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class WindEntityToResponseImpl implements WindEntityToResponse {

    @Override
    public WindTurbineResponse toWindTurbineResponse(WindTurbine windTurbine) {
        if ( windTurbine == null ) {
            return null;
        }

        WindTurbineResponse windTurbineResponse = new WindTurbineResponse();

        windTurbineResponse.setId( windTurbine.getId() );
        windTurbineResponse.setDeviceName( windTurbine.getDeviceName() );
        windTurbineResponse.setLocation( windTurbine.getLocation() );
        windTurbineResponse.setCapacity( windTurbine.getCapacity() );
        windTurbineResponse.setCurrentGeneration( windTurbine.getCurrentGeneration() );
        windTurbineResponse.setStatus( windTurbine.getStatus() );

        return windTurbineResponse;
    }

    @Override
    public List<WindTurbineResponse> listToWindTurbineResponse(List<WindTurbine> windTurbines) {
        if ( windTurbines == null ) {
            return null;
        }

        List<WindTurbineResponse> list = new ArrayList<WindTurbineResponse>( windTurbines.size() );
        for ( WindTurbine windTurbine : windTurbines ) {
            list.add( toWindTurbineResponse( windTurbine ) );
        }

        return list;
    }
}
