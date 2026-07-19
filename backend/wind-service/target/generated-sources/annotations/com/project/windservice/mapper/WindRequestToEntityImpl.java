package com.project.windservice.mapper;

import com.project.windservice.dto.WindTurbineRequest;
import com.project.windservice.entity.WindTurbine;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-19T19:27:30+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class WindRequestToEntityImpl implements WindRequestToEntity {

    @Override
    public WindTurbine toWindTurbine(WindTurbineRequest request) {
        if ( request == null ) {
            return null;
        }

        WindTurbine windTurbine = new WindTurbine();

        windTurbine.setDeviceName( request.getDeviceName() );
        windTurbine.setLocation( request.getLocation() );
        windTurbine.setCapacity( request.getCapacity() );
        windTurbine.setCurrentGeneration( request.getCurrentGeneration() );
        windTurbine.setStatus( request.getStatus() );

        return windTurbine;
    }

    @Override
    public void updateEntity(WindTurbineRequest request, WindTurbine windTurbine) {
        if ( request == null ) {
            return;
        }

        if ( request.getDeviceName() != null ) {
            windTurbine.setDeviceName( request.getDeviceName() );
        }
        if ( request.getLocation() != null ) {
            windTurbine.setLocation( request.getLocation() );
        }
        if ( request.getCapacity() != null ) {
            windTurbine.setCapacity( request.getCapacity() );
        }
        if ( request.getCurrentGeneration() != null ) {
            windTurbine.setCurrentGeneration( request.getCurrentGeneration() );
        }
        if ( request.getStatus() != null ) {
            windTurbine.setStatus( request.getStatus() );
        }
    }
}
