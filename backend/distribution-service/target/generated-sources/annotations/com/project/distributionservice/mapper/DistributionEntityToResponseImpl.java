package com.project.distributionservice.mapper;

import com.project.distributionservice.dto.DistributionResponse;
import com.project.distributionservice.entity.DistributionHistory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-18T22:57:06+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class DistributionEntityToResponseImpl implements DistributionEntityToResponse {

    @Override
    public DistributionResponse toResponse(DistributionHistory history) {
        if ( history == null ) {
            return null;
        }

        DistributionResponse distributionResponse = new DistributionResponse();

        distributionResponse.setId( history.getId() );
        distributionResponse.setRequiredUnits( history.getRequiredUnits() );
        distributionResponse.setRenewableUnits( history.getRenewableUnits() );
        distributionResponse.setBatteryUsed( history.getBatteryUsed() );
        distributionResponse.setExcessUnitsStored( history.getExcessUnitsStored() );
        distributionResponse.setStatus( history.getStatus() );
        distributionResponse.setDistributedAt( history.getDistributedAt() );

        return distributionResponse;
    }

    @Override
    public List<DistributionResponse> toListResponse(List<DistributionHistory> histories) {
        if ( histories == null ) {
            return null;
        }

        List<DistributionResponse> list = new ArrayList<DistributionResponse>( histories.size() );
        for ( DistributionHistory distributionHistory : histories ) {
            list.add( toResponse( distributionHistory ) );
        }

        return list;
    }
}
