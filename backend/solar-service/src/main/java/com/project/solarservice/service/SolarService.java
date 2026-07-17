package com.project.solarservice.service;

import com.project.solarservice.dto.*;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import com.project.solarservice.exception.SolarPanelNotFoundException;
import com.project.solarservice.mapper.*;
import com.project.solarservice.repository.SolarHistoryRepository;
import com.project.solarservice.repository.SolarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolarService {

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
        Solar solar = dtoToEntitymapper.dtoToSolar(solarRequestDto);
        solarRepository.save(solar);
        return entityDtoMapper.entityToDto(solar);
    }

    public List<Solar> getAllPanelDetails(){
        return solarRepository.findAll();
    }

    public Object getPanelById(Long id){
        Solar solar =  solarRepository.findById(id).orElseThrow( () -> new SolarPanelNotFoundException("The requested solar not found/exist"));
        return entityDtoMapper.entityToDto(solar);
    }

    public SolarResponseDto updatePanel(Long id,SolarRequestDto solarRequestDto){
        Solar solar = solarRepository.findById(id).orElseThrow( () -> new SolarPanelNotFoundException("The requested solar not found/exist"));
        dtoToEntitymapper.updateSolarFromDto(solarRequestDto,solar);
        solarRepository.save(solar);
        return entityDtoMapper.entityToDto(solar);
    }

    public String deletePanel(Long id){
        solarRepository.deleteById(id);
        return "Solar panel with id " + id + " is deleted successfully";
    }

    public SolarGenerationResponse recordGeneration(Long panelId, SolarGenerationRequestDto solarGenerationRequestDto){
        Solar solar = solarRepository.findById(panelId).orElseThrow( () -> new SolarPanelNotFoundException("The requested panel not found/exist"));

        SolarGenerationEntity solarGenerationEntity = entityFromDtoMapper.entityFromDto(solarGenerationRequestDto);
        solarGenerationEntity.setGenerated_at(LocalDateTime.now());
        solarGenerationEntity.setSolar(solar);
        solarHistoryRepository.save(solarGenerationEntity);
        solar.setCurrent_generation(solarGenerationRequestDto.getGenerated_units());

        solarRepository.save(solar);
        return generationResponseMapper.GenerationRequestToResponse(solarGenerationEntity);
    }

    public List<SolarGenerationResponse> getAllGenerations(){
        List<SolarGenerationEntity> solarGenerationEntityList = solarHistoryRepository.findAll();
        return generationResponseMapper.GenrationRequestToResponseAll(solarGenerationEntityList);
    }

    public List<SolarGenerationResponse> getGenerationsById(Long panelId){
        solarRepository.findById(panelId).orElseThrow( () -> new SolarPanelNotFoundException("The requested panel not found/exits"));

        List<SolarGenerationEntity> generationEntities = solarHistoryRepository.findBySolarId(panelId);
        return generationResponseMapper.GenrationRequestToResponseAll(generationEntities);
    }

    public List<FaultResponse> getAllFaults(){
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
        return faults;
    }

}
