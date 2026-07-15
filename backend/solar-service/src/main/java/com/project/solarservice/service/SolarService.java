package com.project.solarservice.service;

import com.project.solarservice.dto.SolarGenerationRequestDto;
import com.project.solarservice.dto.SolarGenerationResponse;
import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import com.project.solarservice.exception.SolarPanelNotFoundException;
import com.project.solarservice.mapper.GenerationEntityFromDto;
import com.project.solarservice.mapper.GenerationResponse;
import com.project.solarservice.mapper.SolarDtoEntity;
import com.project.solarservice.mapper.SolarEntityDto;
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

    public SolarService(SolarRepository solarRepository, SolarHistoryRepository solarHistoryRepository, SolarDtoEntity dtoToEntitymapper, SolarEntityDto entityDtoMapper, GenerationEntityFromDto entityFromDtoMapper, GenerationResponse generationResponseMapper) {
        this.solarRepository = solarRepository;
        this.solarHistoryRepository = solarHistoryRepository;
        this.dtoToEntitymapper = dtoToEntitymapper;
        this.entityDtoMapper = entityDtoMapper;
        this.entityFromDtoMapper = entityFromDtoMapper;
        this.generationResponseMapper = generationResponseMapper;
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
}
