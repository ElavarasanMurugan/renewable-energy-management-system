package com.project.distributionservice.mapper;

import com.project.distributionservice.dto.DistributionResponse;
import com.project.distributionservice.entity.DistributionHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistributionEntityToResponse {
    DistributionResponse toResponse(DistributionHistory history);
}
