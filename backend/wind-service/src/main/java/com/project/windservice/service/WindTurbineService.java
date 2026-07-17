package com.project.windservice.service;

import com.project.windservice.dto.*;
import com.project.windservice.entity.WindGenerationHistory;
import com.project.windservice.entity.WindTurbine;
import com.project.windservice.exception.CurrentGenerationException;
import com.project.windservice.exception.WindTurbineNotFoundException;
import com.project.windservice.mapper.*;
import com.project.windservice.repository.WindGenerationRepository;
import com.project.windservice.repository.WindTurbineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WindTurbineService {
    private final WindTurbineRepository windTurbineRepository;
    private final WindGenerationRepository windGenerationRepository;
    private final WindRequestToEntity entityMapper;
    private final WindEntityToResponse responseMapper;
    private final GenerationRequestToEntity generationRequestToEntity;
    private final GenerationEntityToResponse generationEntityToResponse;
    private final WindToFaultResponse entityToFault;

    public WindTurbineService(WindTurbineRepository windTurbineRepository, WindGenerationRepository windGenerationRepository, WindRequestToEntity entityMapper, WindEntityToResponse responseMapper, GenerationRequestToEntity generationRequestToEntity, GenerationEntityToResponse generationEntityToResponse, WindToFaultResponse entityToFault) {
        this.windTurbineRepository = windTurbineRepository;
        this.windGenerationRepository = windGenerationRepository;
        this.entityMapper = entityMapper;
        this.responseMapper = responseMapper;
        this.generationRequestToEntity = generationRequestToEntity;
        this.generationEntityToResponse = generationEntityToResponse;
        this.entityToFault = entityToFault;
    }

    public WindTurbineResponse registerTurbine(WindTurbineRequest request){
        if(request.getCapacity() < request.getCurrentGeneration()){
            throw new CurrentGenerationException("The generated power must be less than or equal to the capacity");
        }
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
        if(request.getCapacity() < request.getCurrentGeneration()){
            throw new CurrentGenerationException("The generated current must be less than or equal to the capacity");
        }
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

    public WindGenerationResponse recordGeneration(Long turbineId,WindGenerationRequest request){
        WindTurbine windTurbine = windTurbineRepository.findById(turbineId).orElseThrow( () -> new WindTurbineNotFoundException("The requested turbine not found/exists"));

        WindGenerationHistory windGenerationHistory = generationRequestToEntity.toGenerationEntity(request);
        windGenerationHistory.setGeneratedAt(LocalDateTime.now());
        windGenerationHistory.setWindTurbine(windTurbine);

        if(windTurbine.getCapacity() < request.getGeneratedPower()){
            throw new CurrentGenerationException("The generated power must be less than or equal to the capacity");
        }
        windTurbine.setCurrentGeneration(request.getGeneratedPower());

        windGenerationRepository.save(windGenerationHistory);
        return generationEntityToResponse.toGenerationResponse(windGenerationHistory);
    }

    public List<WindGenerationResponse> getAllGeneration(){
        List<WindGenerationHistory> histories = windGenerationRepository.findAll();
        return generationEntityToResponse.toListGeneration(histories);
    }
    public List<WindGenerationResponse> getGenerationByTurbine(Long turbineId){
        windTurbineRepository.findById(turbineId).orElseThrow(() -> new WindTurbineNotFoundException("The requested turbine is not found/exists"));
        List<WindGenerationHistory> histories = windGenerationRepository.findByWindTurbineId(turbineId);

        return generationEntityToResponse.toListGeneration(histories);
    }

    public List<FaultResponse> getAllFaults(){
        List<WindTurbine> windTurbines = windTurbineRepository.findAll();
        List<FaultResponse> faults = new ArrayList<>();

        for(WindTurbine windTurbine : windTurbines){
            if(windTurbine.getStatus().equalsIgnoreCase("ACTIVE") && windTurbine.getCurrentGeneration() == 0) {
                FaultResponse response = entityToFault.toFaultResponse(windTurbine);
                response.setFault(true);
                response.setMessage("Fault alert!. Zero output during active hours");
                faults.add(response);
            }
        }
        return faults;
    }
}
