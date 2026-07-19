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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class WindTurbineService {
    private static final Logger logger =
            LoggerFactory.getLogger(WindTurbineService.class);

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
        logger.info("Registering new wind turbine: {}", request.getDeviceName());
        if(request.getCapacity() < request.getCurrentGeneration()){
            logger.error(
                    "Current generation {} exceeds capacity {}",
                    request.getCurrentGeneration(),
                    request.getCapacity());
            throw new CurrentGenerationException("The generated power must be less than or equal to the capacity");
        }
        WindTurbine windTurbine = entityMapper.toWindTurbine(request);
        windTurbine.setCreatedAt(LocalDateTime.now());
        windTurbine.setUpdatedAt(LocalDateTime.now());
        windTurbineRepository.save(windTurbine);
        logger.info("Wind turbine registered successfully with ID: {}", windTurbine.getId());
        return responseMapper.toWindTurbineResponse(windTurbine);
    }

    public List<WindTurbineResponse> getAllWindTurbines() {
        logger.info("Fetching all wind turbines");
        List<WindTurbine> windTurbines = windTurbineRepository.findAll();
        logger.info("Retrieved {} wind turbines", windTurbines.size());
        return responseMapper.listToWindTurbineResponse(windTurbines);
    }

    public WindTurbineResponse getTurbineById(Long id){
        logger.info("Fetching wind turbine with ID: {}", id);
        WindTurbine windTurbine = windTurbineRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Wind turbine not found with ID: {}", id);
                    return new WindTurbineNotFoundException(
                            "The requested turbine is not found/exists");
                });
        logger.info("Successfully retrieved wind turbine with ID: {}", id);
        return responseMapper.toWindTurbineResponse(windTurbine);
    }
    public WindTurbineResponse updateTurbine(Long id, WindTurbineRequest request){
        logger.info("Updating wind turbine with ID: {}", id);
        WindTurbine windTurbine = windTurbineRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Wind turbine not found with ID: {}", id);
                    return new WindTurbineNotFoundException(
                            "The requested turbine is not found/exists");
                });

        if(request.getCapacity() < request.getCurrentGeneration()){
            logger.error(
                    "Current generation {} exceeds capacity {}",
                    request.getCurrentGeneration(),
                    request.getCapacity());
            throw new CurrentGenerationException("The generated current must be less than or equal to the capacity");
        }
        entityMapper.updateEntity(request,windTurbine);
        windTurbine.setUpdatedAt(LocalDateTime.now());
        windTurbineRepository.save(windTurbine);
        logger.info("Wind turbine updated successfully with ID: {}", id);
        return responseMapper.toWindTurbineResponse(windTurbine);
    }

    public String deleteTurbine(Long id){
        logger.info("Deleting wind turbine with ID: {}", id);
        windTurbineRepository.deleteById(id);
        logger.info("Wind turbine deleted successfully with ID: {}", id);
        return "Wind Turbine with id " + id + " is deleted successfully";
    }

    public String deleteAllTurbines(){
        logger.info("Deleting all wind turbines");
        windTurbineRepository.deleteAll();
        logger.info("All wind turbines deleted successfully");
        return "All the wind turbines are removed successfully";
    }

    public WindGenerationResponse recordGeneration(Long turbineId,WindGenerationRequest request){
        logger.info("Recording power generation for wind turbine ID: {}", turbineId);

        WindTurbine windTurbine = windTurbineRepository.findById(turbineId)
                .orElseThrow(() -> {
                    logger.error("Wind turbine not found with ID: {}", turbineId);
                    return new WindTurbineNotFoundException(
                            "The requested turbine not found/exists");
                });

        WindGenerationHistory windGenerationHistory = generationRequestToEntity.toGenerationEntity(request);
        windGenerationHistory.setGeneratedAt(LocalDateTime.now());
        windGenerationHistory.setWindTurbine(windTurbine);

        if(windTurbine.getCapacity() < request.getGeneratedPower()){
            logger.error("Generated power {} exceeds capacity {}",request.getGeneratedPower(),windTurbine.getCapacity());
            throw new CurrentGenerationException("The generated power must be less than or equal to the capacity");
        }
        windTurbine.setCurrentGeneration(request.getGeneratedPower());

        windGenerationRepository.save(windGenerationHistory);
        logger.info("Recorded {} units for wind turbine ID: {}",request.getGeneratedPower(),turbineId);
        return generationEntityToResponse.toGenerationResponse(windGenerationHistory);
    }

    public List<WindGenerationResponse> getAllGeneration(){
        logger.info("Fetching generation history for all wind turbines");
        List<WindGenerationHistory> histories = windGenerationRepository.findAll();
        logger.info("Retrieved {} generation records", histories.size());
        return generationEntityToResponse.toListGeneration(histories);
    }
    public List<WindGenerationResponse> getGenerationByTurbine(Long turbineId){
        logger.info("Fetching generation history for wind turbine ID: {}", turbineId);
        windTurbineRepository.findById(turbineId)
                .orElseThrow(() -> {
                    logger.error("Wind turbine not found with ID: {}", turbineId);
                    return new WindTurbineNotFoundException(
                            "The requested turbine is not found/exists");
                });
        List<WindGenerationHistory> histories = windGenerationRepository.findByWindTurbineId(turbineId);

        logger.info("Retrieved {} generation records for wind turbine ID: {}",histories.size(),turbineId);
        return generationEntityToResponse.toListGeneration(histories);
    }

    public List<FaultResponse> getAllFaults(){
        logger.info("Fetching wind turbine fault report");
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
        logger.info("Detected {} faulty wind turbines", faults.size());
        return faults;
    }
    public ReportResponse getDailyReports(LocalDate date){
        logger.info("Generating daily wind report for {}", date);
        List<WindGenerationHistory> histories = windGenerationRepository.findAll();
        double total = histories.stream()
                .filter( h-> h.getGeneratedAt().toLocalDate().equals(date))
                .mapToDouble(WindGenerationHistory :: getGeneratedPower)
                .sum();

        ReportResponse response = new ReportResponse();
        response.setDate(date);
        response.setTotalGeneratedPower(total);

        logger.info("Total wind generation on {} is {} units",date,total);
        return response;
    }

    public ReportResponse getDailyReportsById(Long id, LocalDate date){
        logger.info("Generating daily report for wind turbine {} on {}",id,date);
        List<WindGenerationHistory> histories = windGenerationRepository.findByWindTurbineId(id);
        double total = histories.stream()
                .filter( h-> h.getGeneratedAt().toLocalDate().equals(date))
                .mapToDouble(WindGenerationHistory :: getGeneratedPower)
                .sum();

        ReportResponse response = new ReportResponse();
        response.setDate(date);
        response.setTotalGeneratedPower(total);
        response.setTurbineId(id);
        logger.info("Wind turbine {} generated {} units on {}",id,total,date);
        return response;
    }

    public ReportResponse getAllReportsById(Long id){
        logger.info("Generating overall report for wind turbine {}", id);
        List<WindGenerationHistory> histories = windGenerationRepository.findByWindTurbineId(id);
        double total = histories.stream()
                .mapToDouble(WindGenerationHistory::getGeneratedPower)
                .sum();

        ReportResponse response = new ReportResponse();
        response.setTotalGeneratedPower(total);
        response.setTurbineId(id);
        logger.info("Wind turbine {} generated a total of {} units",id,total);
        return response;
    }
}
