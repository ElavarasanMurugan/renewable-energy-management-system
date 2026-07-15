package com.project.solarservice.controller;

import com.project.solarservice.dto.SolarGenerationRequestDto;
import com.project.solarservice.dto.SolarGenerationResponse;
import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.entity.SolarGenerationEntity;
import com.project.solarservice.service.SolarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/solar-panels")
public class SolarController {

    private final SolarService solarService;

    public SolarController(SolarService solarService) {
        this.solarService = solarService;
    }

    @PostMapping
    public SolarResponseDto registerPanels(@Valid @RequestBody SolarRequestDto solarRequestDto){
        return solarService.registerPanels(solarRequestDto);
    }

    @GetMapping
    public List<Solar> getAllPanelDetails(){
        return solarService.getAllPanelDetails();
    }

    @GetMapping("/{id}")
    public SolarResponseDto getPanelById(@PathVariable Long id){
        return (SolarResponseDto) solarService.getPanelById(id);
    }

    @PutMapping("/{id}")
    public SolarResponseDto updatePanel(@PathVariable Long id,@Valid @RequestBody SolarRequestDto solarRequestDto){
        return solarService.updatePanel(id,solarRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deletePanel(@PathVariable Long id){
        return solarService.deletePanel(id);
    }

    @PostMapping("/{panelId}/generation")
    public SolarGenerationResponse recordGeneration(@PathVariable Long panelId, @Valid @RequestBody SolarGenerationRequestDto solarGenerationRequestDto){
        return solarService.recordGeneration(panelId,solarGenerationRequestDto);
    }

    @GetMapping("/generation")
    public List<SolarGenerationResponse> getAllGenerations(){
        return solarService.getAllGenerations();
    }

    @GetMapping("/{panelId}/generation")
    public List<SolarGenerationResponse> getGenerationsById(@PathVariable Long panelId){
        return solarService.getGenerationsById(panelId);
    }
}
