package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.HistoryResponse;
import com.project.batteryservice.entity.Battery;
import com.project.batteryservice.entity.BatteryHistory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-19T19:27:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class HistoryEntityToResponseImpl implements HistoryEntityToResponse {

    @Override
    public HistoryResponse toHistoryResponse(BatteryHistory history) {
        if ( history == null ) {
            return null;
        }

        HistoryResponse historyResponse = new HistoryResponse();

        historyResponse.setId( history.getId() );
        historyResponse.setBatteryId( historyBatteryId( history ) );
        historyResponse.setBatteryName( historyBatteryBatteryName( history ) );
        historyResponse.setCapacity( historyBatteryCapacity( history ) );
        historyResponse.setCurrentCharge( historyBatteryCurrentCharge( history ) );
        historyResponse.setStatus( historyBatteryStatus( history ) );
        historyResponse.setUnits( history.getUnits() );

        return historyResponse;
    }

    private Long historyBatteryId(BatteryHistory batteryHistory) {
        Battery battery = batteryHistory.getBattery();
        if ( battery == null ) {
            return null;
        }
        return battery.getId();
    }

    private String historyBatteryBatteryName(BatteryHistory batteryHistory) {
        Battery battery = batteryHistory.getBattery();
        if ( battery == null ) {
            return null;
        }
        return battery.getBatteryName();
    }

    private Double historyBatteryCapacity(BatteryHistory batteryHistory) {
        Battery battery = batteryHistory.getBattery();
        if ( battery == null ) {
            return null;
        }
        return battery.getCapacity();
    }

    private Double historyBatteryCurrentCharge(BatteryHistory batteryHistory) {
        Battery battery = batteryHistory.getBattery();
        if ( battery == null ) {
            return null;
        }
        return battery.getCurrentCharge();
    }

    private String historyBatteryStatus(BatteryHistory batteryHistory) {
        Battery battery = batteryHistory.getBattery();
        if ( battery == null ) {
            return null;
        }
        return battery.getStatus();
    }
}
