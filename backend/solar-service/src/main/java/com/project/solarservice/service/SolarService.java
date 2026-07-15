package com.project.solarservice.service;

import com.project.solarservice.dto.SolarGenerationRequestDto;
import com.project.solarservice.dto.SolarGenerationResponse;
import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import com.project.solarservice.exception.SolarPanelNotFoundException;
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

    public SolarService(SolarRepository solarRepository, SolarHistoryRepository solarHistoryRepository) {
        this.solarRepository = solarRepository;
        this.solarHistoryRepository = solarHistoryRepository;
    }

    public Solar registerPanels(SolarRequestDto solarRequestDto){
        Solar solar = new Solar();
        solar.setId(solarRequestDto.getId());
        solar.setPanel_name(solarRequestDto.getPanel_name());
        solar.setLocation(solarRequestDto.getLocation());
        solar.setCapacity_kw(solarRequestDto.getCapacity_kw());
        solar.setStatus(solarRequestDto.getStatus());
        solar.setCurrent_generation(solarRequestDto.getCurrent_generation());

        return solarRepository.save(solar);
    }

    public List<Solar> getAllPanelDetails(){
        return solarRepository.findAll();
    }

    public Object getPanelById(Long id){
        return solarRepository.findById(id).orElseThrow( () -> new SolarPanelNotFoundException("The requested solar not found/exist"));
    }

    public Solar updatePanel(Long id,SolarRequestDto solarRequestDto){
        Solar solar = solarRepository.findById(id).orElseThrow( () -> new SolarPanelNotFoundException("The requested solar not found/exist"));
        solar.setPanel_name(solarRequestDto.getPanel_name());
        solar.setLocation(solarRequestDto.getLocation());
        solar.setCapacity_kw(solarRequestDto.getCapacity_kw());
        solar.setStatus(solarRequestDto.getStatus());
        solar.setCurrent_generation(solarRequestDto.getCurrent_generation());

        return solarRepository.save(solar);
    }

    public String deletePanel(Long id){
        solarRepository.deleteById(id);
        return "Solar panel with id " + id + " is deleted successfully";
    }

    public SolarGenerationResponse recordGeneration(Long panelId, SolarGenerationRequestDto solarGenerationRequestDto){
        Solar solar = solarRepository.findById(panelId).orElseThrow( () -> new SolarPanelNotFoundException("The requested panel not found/exist"));

        SolarGenerationEntity solarGenerationEntity = new SolarGenerationEntity();
        solarGenerationEntity.setGenerated_units(solarGenerationRequestDto.getGenerated_units());
        solarGenerationEntity.setGenerated_at(LocalDateTime.now());
        solarGenerationEntity.setSolar(solar);

        solarHistoryRepository.save(solarGenerationEntity);
        solar.setCurrent_generation(solarGenerationRequestDto.getGenerated_units());

        solarRepository.save(solar);

        SolarGenerationResponse solarGenerationResponse = new SolarGenerationResponse();
        solarGenerationResponse.setId(solarGenerationEntity.getId());
        solarGenerationResponse.setPanelId(solar.getId());
        solarGenerationResponse.setPanel_name(solar.getPanel_name());
        solarGenerationResponse.setGenerated_units(solarGenerationRequestDto.getGenerated_units());
        solarGenerationResponse.setGeneratedAt(solarGenerationEntity.getGenerated_at());

        return solarGenerationResponse;
    }

    public List<SolarGenerationResponse> getAllGenerations(){
        List<SolarGenerationEntity> solarGenerationEntityList = solarHistoryRepository.findAll();

        List<SolarGenerationResponse> responseList = new ArrayList<>();

        for(SolarGenerationEntity solarGeneration : solarGenerationEntityList){
            SolarGenerationResponse solarGenerationResponse = new SolarGenerationResponse();
            solarGenerationResponse.setId(solarGeneration.getId());
            solarGenerationResponse.setPanelId(solarGeneration.getSolar().getId());
            solarGenerationResponse.setPanel_name(solarGeneration.getSolar().getPanel_name());
            solarGenerationResponse.setGenerated_units(solarGeneration.getGenerated_units());
            solarGenerationResponse.setGeneratedAt(solarGeneration.getGenerated_at());

            responseList.add(solarGenerationResponse);
        }

        return responseList;
    }

    public List<SolarGenerationResponse> getGenerationsById(Long panelId){
        solarRepository.findById(panelId).orElseThrow( () -> new SolarPanelNotFoundException("The requested panel not found/exits"));

        List<SolarGenerationEntity> generationEntities = solarHistoryRepository.findBySolarId(panelId);
        List<SolarGenerationResponse> responseList = new ArrayList<>();

        for(SolarGenerationEntity solarGeneration : generationEntities){
            SolarGenerationResponse solarGenerationResponse = new SolarGenerationResponse();
            solarGenerationResponse.setId(solarGeneration.getId());
            solarGenerationResponse.setPanelId(solarGeneration.getSolar().getId());
            solarGenerationResponse.setPanel_name(solarGeneration.getSolar().getPanel_name());
            solarGenerationResponse.setGenerated_units(solarGeneration.getGenerated_units());
            solarGenerationResponse.setGeneratedAt(solarGeneration.getGenerated_at());

            responseList.add(solarGenerationResponse);
        }

        return responseList;
    }
}
