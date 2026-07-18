package com.project.distributionservice.mapper;

import com.project.distributionservice.dto.DistributionRequest;
import com.project.distributionservice.entity.DistributionHistory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T22:23:20+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class DistributionRequestToEntityImpl implements DistributionRequestToEntity {

    @Override
    public DistributionHistory toDistribution(DistributionRequest request) {
        if ( request == null ) {
            return null;
        }

        DistributionHistory distributionHistory = new DistributionHistory();

        distributionHistory.setRequiredUnits( request.getRequiredUnits() );

        return distributionHistory;
    }
}
