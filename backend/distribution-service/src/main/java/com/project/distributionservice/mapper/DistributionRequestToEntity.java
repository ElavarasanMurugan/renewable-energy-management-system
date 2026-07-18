package com.project.distributionservice.mapper;

import com.project.distributionservice.dto.DistributionRequest;
import com.project.distributionservice.entity.DistributionHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistributionRequestToEntity {
    DistributionHistory toDistribution(DistributionRequest request);
}
