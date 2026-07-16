package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.BatteryRequest;
import com.project.batteryservice.entity.Battery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BatteryRequestToEntity {
    Battery toEntity(BatteryRequest request);
    void updateEntity(BatteryRequest request, @MappingTarget Battery battery);
}
