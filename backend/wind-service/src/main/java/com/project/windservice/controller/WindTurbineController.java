package com.project.windservice.controller;

import com.project.windservice.dto.*;
import com.project.windservice.service.WindTurbineService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wind-turbines")
public class WindTurbineController {
    private final WindTurbineService windTurbineService;

    public WindTurbineController(WindTurbineService windTurbineService) {
        this.windTurbineService = windTurbineService;
    }

    @PostMapping
    public WindTurbineResponse registerTurbine(@Valid @RequestBody WindTurbineRequest request){
        return windTurbineService.registerTurbine(request);
    }

    @GetMapping
    public List<WindTurbineResponse> getAllWindTurbines(){
        return windTurbineService.getAllWindTurbines();
    }

    @GetMapping("/{id}")
    public WindTurbineResponse getTurbineById(@PathVariable Long id){
        return windTurbineService.getTurbineById(id);
    }

    @PutMapping("/{id}")
    public WindTurbineResponse updateTurbine(@PathVariable Long id, @Valid @RequestBody WindTurbineRequest request){
        return windTurbineService.updateTurbine(id,request);
    }

    @DeleteMapping("/{id}")
    public String deleteTurbine(@PathVariable Long id){
        return windTurbineService.deleteTurbine(id);
    }

    @DeleteMapping
    public String deleteAllTurbines(){
        return windTurbineService.deleteAllTurbines();
    }

    @PostMapping("/{turbineId}/generation")
    public WindGenerationResponse recordGeneration(@PathVariable Long turbineId, @Valid @RequestBody WindGenerationRequest request){
        return windTurbineService.recordGeneration(turbineId,request);
    }

    @GetMapping("/generation")
    public List<WindGenerationResponse> getAllGeneration(){
        return windTurbineService.getAllGeneration();
    }

    @GetMapping("/{turbineId}/generation")
    public List<WindGenerationResponse> getGenerationByTurbine(@PathVariable Long turbineId){
        return windTurbineService.getGenerationByTurbine(turbineId);
    }

    @GetMapping("/faults")
    public List<FaultResponse> getAllFaults(){
        return windTurbineService.getAllFaults();
    }
}
