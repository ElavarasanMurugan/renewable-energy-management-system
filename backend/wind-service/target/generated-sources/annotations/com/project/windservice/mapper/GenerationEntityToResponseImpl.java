package com.project.windservice.mapper;

import com.project.windservice.dto.WindGenerationResponse;
import com.project.windservice.entity.WindGenerationHistory;
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
public class GenerationEntityToResponseImpl implements GenerationEntityToResponse {

    @Override
    public WindGenerationResponse toGenerationResponse(WindGenerationHistory history) {
        if ( history == null ) {
            return null;
        }

        WindGenerationResponse windGenerationResponse = new WindGenerationResponse();

        windGenerationResponse.setTurbineId( historyWindTurbineId( history ) );
        windGenerationResponse.setDeviceName( historyWindTurbineDeviceName( history ) );
        windGenerationResponse.setLocation( historyWindTurbineLocation( history ) );
        windGenerationResponse.setCurrentGeneration( historyWindTurbineCurrentGeneration( history ) );
        windGenerationResponse.setCapacity( historyWindTurbineCapacity( history ) );
        windGenerationResponse.setId( history.getId() );
        windGenerationResponse.setGeneratedAt( history.getGeneratedAt() );
        windGenerationResponse.setGeneratedPower( history.getGeneratedPower() );

        return windGenerationResponse;
    }

    @Override
    public List<WindGenerationResponse> toListGeneration(List<WindGenerationHistory> windGenerationHistories) {
        if ( windGenerationHistories == null ) {
            return null;
        }

        List<WindGenerationResponse> list = new ArrayList<WindGenerationResponse>( windGenerationHistories.size() );
        for ( WindGenerationHistory windGenerationHistory : windGenerationHistories ) {
            list.add( toGenerationResponse( windGenerationHistory ) );
        }

        return list;
    }

    private Long historyWindTurbineId(WindGenerationHistory windGenerationHistory) {
        WindTurbine windTurbine = windGenerationHistory.getWindTurbine();
        if ( windTurbine == null ) {
            return null;
        }
        return windTurbine.getId();
    }

    private String historyWindTurbineDeviceName(WindGenerationHistory windGenerationHistory) {
        WindTurbine windTurbine = windGenerationHistory.getWindTurbine();
        if ( windTurbine == null ) {
            return null;
        }
        return windTurbine.getDeviceName();
    }

    private String historyWindTurbineLocation(WindGenerationHistory windGenerationHistory) {
        WindTurbine windTurbine = windGenerationHistory.getWindTurbine();
        if ( windTurbine == null ) {
            return null;
        }
        return windTurbine.getLocation();
    }

    private Double historyWindTurbineCurrentGeneration(WindGenerationHistory windGenerationHistory) {
        WindTurbine windTurbine = windGenerationHistory.getWindTurbine();
        if ( windTurbine == null ) {
            return null;
        }
        return windTurbine.getCurrentGeneration();
    }

    private Double historyWindTurbineCapacity(WindGenerationHistory windGenerationHistory) {
        WindTurbine windTurbine = windGenerationHistory.getWindTurbine();
        if ( windTurbine == null ) {
            return null;
        }
        return windTurbine.getCapacity();
    }
}
