package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.BatteryResponse;
import com.project.batteryservice.entity.Battery;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T21:56:22+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class BatteryEntityToResponseImpl implements BatteryEntityToResponse {

    @Override
    public BatteryResponse toResponse(Battery battery) {
        if ( battery == null ) {
            return null;
        }

        BatteryResponse batteryResponse = new BatteryResponse();

        batteryResponse.setId( battery.getId() );
        batteryResponse.setBatteryName( battery.getBatteryName() );
        batteryResponse.setCapacity( battery.getCapacity() );
        batteryResponse.setLocation( battery.getLocation() );
        batteryResponse.setStatus( battery.getStatus() );
        batteryResponse.setCurrentCharge( battery.getCurrentCharge() );

        return batteryResponse;
    }

    @Override
    public List<BatteryResponse> toListResponse(List<Battery> batteryList) {
        if ( batteryList == null ) {
            return null;
        }

        List<BatteryResponse> list = new ArrayList<BatteryResponse>( batteryList.size() );
        for ( Battery battery : batteryList ) {
            list.add( toResponse( battery ) );
        }

        return list;
    }
}
