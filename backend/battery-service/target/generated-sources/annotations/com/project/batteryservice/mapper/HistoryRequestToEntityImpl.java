package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.HistoryRequest;
import com.project.batteryservice.entity.BatteryHistory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T21:56:22+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class HistoryRequestToEntityImpl implements HistoryRequestToEntity {

    @Override
    public BatteryHistory toHistoryEntity(HistoryRequest request) {
        if ( request == null ) {
            return null;
        }

        BatteryHistory batteryHistory = new BatteryHistory();

        batteryHistory.setUnits( request.getUnits() );

        return batteryHistory;
    }

    @Override
    public void updateEntity(HistoryRequest request, BatteryHistory history) {
        if ( request == null ) {
            return;
        }

        history.setUnits( request.getUnits() );
    }
}
