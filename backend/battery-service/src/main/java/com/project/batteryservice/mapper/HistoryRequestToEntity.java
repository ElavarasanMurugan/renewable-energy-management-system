package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.HistoryRequest;
import com.project.batteryservice.entity.BatteryHistory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HistoryRequestToEntity {
    BatteryHistory toHistoryEntity(HistoryRequest request);
    void updateEntity(HistoryRequest request, @MappingTarget BatteryHistory history);
}
