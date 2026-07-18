package com.project.distributionservice.mapper;

import com.project.distributionservice.dto.DistributionRequest;
import com.project.distributionservice.entity.DistributionHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistributionRequestToEntity {
     @Mapping(source = "requiredUnits",target = "requiredUnits")
    DistributionHistory toDistribution(DistributionRequest request);
}
