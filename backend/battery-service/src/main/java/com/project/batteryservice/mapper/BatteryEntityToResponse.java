package com.project.batteryservice.mapper;

import com.project.batteryservice.dto.BatteryResponse;
import com.project.batteryservice.entity.Battery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BatteryEntityToResponse {
    @Mapping(source = "id",target = "id")
    BatteryResponse toResponse(Battery battery);
    List<BatteryResponse> toListResponse(List<Battery> batteryList);
}
