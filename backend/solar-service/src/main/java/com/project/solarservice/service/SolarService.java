package com.project.solarservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.solarservice.dto.*;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import com.project.solarservice.exception.SolarPanelNotFoundException;
import com.project.solarservice.mapper.*;
import com.project.solarservice.repository.SolarHistoryRepository;
import com.project.solarservice.repository.SolarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SolarService {

    private static final Logger logger =
            LoggerFactory.getLogger(SolarService.class);

    private final SolarRepository solarRepository;

    private final SolarHistoryRepository solarHistoryRepository;

    private final SolarDtoEntity dtoToEntitymapper;

    private final SolarEntityDto entityDtoMapper;

    private final GenerationEntityFromDto entityFromDtoMapper;

    private final GenerationResponse generationResponseMapper;

    private final SolarToFaultResponse entityToFault;
    public SolarService(SolarRepository solarRepository, SolarHistoryRepository solarHistoryRepository, SolarDtoEntity dtoToEntitymapper, SolarEntityDto entityDtoMapper, GenerationEntityFromDto entityFromDtoMapper, GenerationResponse generationResponseMapper, SolarToFaultResponse entityToFault) {
        this.solarRepository = solarRepository;
        this.solarHistoryRepository = solarHistoryRepository;
        this.dtoToEntitymapper = dtoToEntitymapper;
        this.entityDtoMapper = entityDtoMapper;
        this.entityFromDtoMapper = entityFromDtoMapper;
        this.generationResponseMapper = generationResponseMapper;
        this.entityToFault = entityToFault;
    }

    public SolarResponseDto registerPanels(SolarRequestDto solarRequestDto){
        logger.info("Registering new solar panel: {}", solarRequestDto.getPanel_name());
        Solar solar = dtoToEntitymapper.dtoToSolar(solarRequestDto);
        solarRepository.save(solar);
        logger.info("Solar panel registered successfully with ID: {}", solar.getId());
        return entityDtoMapper.entityToDto(solar);
    }

    public List<Solar> getAllPanelDetails(){
        logger.info("Fetching all solar panel details");
        List<Solar> panels = solarRepository.findAll();
        logger.info("Retrieved {} solar panels", panels.size());
        return panels;
    }

    public Object getPanelById(Long id){
        logger.info("Fetching solar panel with ID: {}", id);
        Solar solar = solarRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Solar panel not found with ID: {}", id);
                    return new SolarPanelNotFoundException("The requested solar not found/exist");
                });

        logger.info("Successfully retrieved solar panel with ID: {}", id);
        return entityDtoMapper.entityToDto(solar);
    }

    public SolarResponseDto updatePanel(Long id,SolarRequestDto solarRequestDto){
        logger.info("Updating solar panel with ID: {}", id);
        Solar solar = solarRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Solar panel not found with ID: {}", id);
                    return new SolarPanelNotFoundException("The requested solar not found/exist");
                });
        dtoToEntitymapper.updateSolarFromDto(solarRequestDto, solar);
        solarRepository.save(solar);
        logger.info("Solar panel updated successfully with ID: {}", id);
        return entityDtoMapper.entityToDto(solar);
    }

    public String deletePanel(Long id){
        logger.info("Deleting solar panel with ID: {}", id);
        solarRepository.deleteById(id);
        logger.info("Solar panel deleted successfully with ID: {}", id);
        return "Solar panel with id " + id + " is deleted successfully";
    }

    public SolarGenerationResponse recordGeneration(Long panelId, SolarGenerationRequestDto solarGenerationRequestDto){
        logger.info("Recording generation for solar panel ID: {}", panelId);
        Solar solar = solarRepository.findById(panelId)
                .orElseThrow(() -> {
                    logger.error("Solar panel not found with ID: {}", panelId);
                    return new SolarPanelNotFoundException("The requested panel not found/exist");
                });
        SolarGenerationEntity solarGenerationEntity = entityFromDtoMapper.entityFromDto(solarGenerationRequestDto);
        solarGenerationEntity.setGenerated_at(LocalDateTime.now());
        solarGenerationEntity.setSolar(solar);
        solarHistoryRepository.save(solarGenerationEntity);
        solar.setCurrent_generation(solarGenerationRequestDto.getGenerated_units());

        solarRepository.save(solar);
        logger.info("Recorded {} units for solar panel ID: {}",
                solarGenerationRequestDto.getGenerated_units(),
                panelId);
        return generationResponseMapper.GenerationRequestToResponse(solarGenerationEntity);
    }

    public List<SolarGenerationResponse> getAllGenerations(){
        logger.info("Fetching generation history for all solar panels");

        List<SolarGenerationEntity> history = solarHistoryRepository.findAll();

        logger.info("Retrieved {} generation records", history.size());
        return generationResponseMapper.GenrationRequestToResponseAll(history);
    }

    public List<SolarGenerationResponse> getGenerationsById(Long panelId){
        logger.info("Fetching generation history for solar panel ID: {}", panelId);

        solarRepository.findById(panelId)
                .orElseThrow(() -> {
                    logger.error("Solar panel not found with ID: {}", panelId);
                    return new SolarPanelNotFoundException("The requested panel not found/exits");
                });
        List<SolarGenerationEntity> generationEntities = solarHistoryRepository.findBySolarId(panelId);
        logger.info("Retrieved {} generation records for panel ID: {}",
                generationEntities.size(),
                panelId);
        return generationResponseMapper.GenrationRequestToResponseAll(generationEntities);
    }

    public List<FaultResponse> getAllFaults(){
        logger.info("Fetching solar panel fault report");
        List<Solar> solars = solarRepository.findAll();
        List<FaultResponse> faults = new ArrayList<>();

        for(Solar solar : solars){
            if(solar.getStatus().equalsIgnoreCase("ACTIVE") && solar.getCurrent_generation() == 0) {
                FaultResponse response = entityToFault.toFaultResponse(solar);
                response.setFault(true);
                response.setMessage("Fault alert!. Zero output during active hours");
                faults.add(response);
            }
        }
        logger.info("Detected {} faulty solar panels", faults.size());
        return faults;
    }

    public ReportResponse getDailyReport(LocalDate date){
        logger.info("Generating daily solar report for {}", date);
        List<SolarGenerationEntity> history = solarHistoryRepository.findAll();
        double total = history.stream()
                .filter(h -> h.getGenerated_at().toLocalDate().equals(date))
                .mapToDouble(SolarGenerationEntity::getGenerated_units)
                .sum();
        ReportResponse response = new ReportResponse();
        response.setDate(date);
        response.setTotalGeneratedUnits(total);

        logger.info("Total solar generation on {} is {} units",date,total);
        return response;
    }
    public ReportResponse getDailyReportsById(Long id, LocalDate date){
        logger.info("Generating daily report for solar panel {} on {}",id,date);
        List<SolarGenerationEntity> histories = solarHistoryRepository.findBySolarId(id);
        double total = histories.stream()
                .filter( h-> h.getGenerated_at().toLocalDate().equals(date))
                .mapToDouble(SolarGenerationEntity :: getGenerated_units)
                .sum();

        ReportResponse response = new ReportResponse();
        response.setDate(date);
        response.setTotalGeneratedUnits(total);
        response.setSolarId(id);
        logger.info("Solar panel {} generated {} units on {}",id,total,date);
        return response;
    }

    public ReportResponse getAllReportsById(Long id){
        logger.info("Generating overall report for solar panel {}", id);
        List<SolarGenerationEntity> histories = solarHistoryRepository.findBySolarId(id);
        double total = histories.stream()
                .mapToDouble(SolarGenerationEntity::getGenerated_units)
                .sum();

        ReportResponse response = new ReportResponse();
        response.setTotalGeneratedUnits(total);
        response.setSolarId(id);
        logger.info("Solar panel {} generated a total of {} units",id,total);
        return response;
    }


}
