package com.project.windservice.service;

import com.project.windservice.dto.WindTurbineRequest;
import com.project.windservice.dto.WindTurbineResponse;
import com.project.windservice.entity.WindTurbine;
import com.project.windservice.exception.WindTurbineNotFoundException;
import com.project.windservice.mapper.WindEntityToResponse;
import com.project.windservice.mapper.WindRequestToEntity;
import com.project.windservice.repository.WindTurbineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WindTurbineService {
    private final WindTurbineRepository windTurbineRepository;

    private final WindRequestToEntity entityMapper;
    private final WindEntityToResponse responseMapper;

    public WindTurbineService(WindTurbineRepository windTurbineRepository, WindRequestToEntity entityMapper, WindEntityToResponse responseMapper) {
        this.windTurbineRepository = windTurbineRepository;
        this.entityMapper = entityMapper;
        this.responseMapper = responseMapper;
    }

    public WindTurbineResponse registerTurbine(WindTurbineRequest request){
        WindTurbine windTurbine = entityMapper.toWindTurbine(request);
        windTurbine.setCreatedAt(LocalDateTime.now());
        windTurbine.setUpdatedAt(LocalDateTime.now());
        windTurbineRepository.save(windTurbine);
        return responseMapper.toWindTurbineResponse(windTurbine);
    }

    public List<WindTurbineResponse> getAllWindTurbines() {
        List<WindTurbine> windTurbines = windTurbineRepository.findAll();
        return responseMapper.listToWindTurbineResponse(windTurbines);
    }

    public WindTurbineResponse getTurbineById(Long id){
        WindTurbine windTurbine = windTurbineRepository.findById(id).orElseThrow( () -> new WindTurbineNotFoundException("The requested turbine is not found/exists"));
        return responseMapper.toWindTurbineResponse(windTurbine);
    }
    public WindTurbineResponse updateTurbine(Long id, WindTurbineRequest request){
        WindTurbine windTurbine = windTurbineRepository.findById(id).orElseThrow( () -> new WindTurbineNotFoundException("The requested turbine is not found/exists"));
        entityMapper.updateEntity(request,windTurbine);
        windTurbine.setUpdatedAt(LocalDateTime.now());
        windTurbineRepository.save(windTurbine);
        return responseMapper.toWindTurbineResponse(windTurbine);
    }

    public String deleteTurbine(Long id){
        windTurbineRepository.deleteById(id);
        return "Wind Turbine with id " + id + " is deleted successfully";
    }

    public String deleteAllTurbines(){
        windTurbineRepository.deleteAll();
        return "All the wind turbines are removed successfully";
    }
}
