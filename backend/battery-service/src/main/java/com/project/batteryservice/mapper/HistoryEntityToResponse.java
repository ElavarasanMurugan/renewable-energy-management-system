package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.HistoryResponse;
import com.project.batteryservice.entity.BatteryHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryEntityToResponse {
    @Mapping(source = "id",target = "id")
    @Mapping(source = "battery.id",target = "batteryId")
    @Mapping(source = "battery.batteryName",target = "batteryName")
    @Mapping(source = "battery.capacity",target = "capacity")
    @Mapping(source = "battery.currentCharge",target = "currentCharge")
    @Mapping(source = "battery.status",target = "status")
    HistoryResponse toHistoryResponse(BatteryHistory history);
}
