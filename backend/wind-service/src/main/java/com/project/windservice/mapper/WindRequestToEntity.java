package com.project.windservice.mapper;

import com.project.windservice.dto.WindTurbineRequest;
import com.project.windservice.entity.WindTurbine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WindRequestToEntity {
    WindTurbine toWindTurbine(WindTurbineRequest request);
    void updateEntity(WindTurbineRequest request, @MappingTarget WindTurbine windTurbine);
}
