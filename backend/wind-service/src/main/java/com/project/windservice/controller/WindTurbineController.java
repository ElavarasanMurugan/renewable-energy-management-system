package com.project.windservice.controller;

import com.project.windservice.dto.WindTurbineRequest;
import com.project.windservice.dto.WindTurbineResponse;
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
}
