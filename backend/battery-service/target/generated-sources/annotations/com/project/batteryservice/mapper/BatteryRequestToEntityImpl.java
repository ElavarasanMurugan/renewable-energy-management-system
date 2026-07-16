package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.BatteryRequest;
import com.project.batteryservice.entity.Battery;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-16T23:33:13+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class BatteryRequestToEntityImpl implements BatteryRequestToEntity {

    @Override
    public Battery toEntity(BatteryRequest request) {
        if ( request == null ) {
            return null;
        }

        Battery battery = new Battery();

        battery.setBatteryName( request.getBatteryName() );
        battery.setCapacity( request.getCapacity() );
        battery.setLocation( request.getLocation() );
        battery.setStatus( request.getStatus() );

        return battery;
    }

    @Override
    public void updateEntity(BatteryRequest request, Battery battery) {
        if ( request == null ) {
            return;
        }

        battery.setBatteryName( request.getBatteryName() );
        battery.setCapacity( request.getCapacity() );
        battery.setLocation( request.getLocation() );
        battery.setStatus( request.getStatus() );
    }
}
