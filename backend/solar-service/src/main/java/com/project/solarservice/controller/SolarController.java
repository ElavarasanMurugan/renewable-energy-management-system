package com.project.solarservice.controller;

import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.dto.SolarResponseDto;
import com.project.solarservice.entity.Solar;
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
    public Solar registerPanels(@Valid @RequestBody SolarRequestDto solarRequestDto){
        return solarService.registerPanels(solarRequestDto);
    }

    @GetMapping
    public List<Solar> getAllPanelDetails(){
        return solarService.getAllPanelDetails();
    }

    @GetMapping("/{id}")
    public Solar getPanelById(@PathVariable Long id){
        return (Solar) solarService.getPanelById(id);
    }

    @PutMapping("/{id}")
    public Solar updatePanel(@PathVariable Long id,@Valid @RequestBody SolarRequestDto solarRequestDto){
        return solarService.updatePanel(id,solarRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deletePanel(@PathVariable Long id){
        return solarService.deletePanel(id);
    }
}
