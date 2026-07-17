package com.project.batteryservice.controller;

import com.project.batteryservice.dto.*;
import com.project.batteryservice.entity.BatteryHistory;
import com.project.batteryservice.service.BatteryService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/batteries")
public class BatteryController {
    private final BatteryService batteryService;

    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping
    public BatteryResponse registerBattery(@Valid @RequestBody BatteryRequest request){
        return batteryService.registerBattery(request);
    }

    @GetMapping
    public List<BatteryResponse> getAllBatteries(){
        return batteryService.getAllBatteries();
    }

    @GetMapping("/{id}")
    public BatteryResponse getBatteryById(@PathVariable Long id){
        return batteryService.getBatteryById(id);
    }

    @PutMapping("/{id}")
    public BatteryResponse updateBattery(@PathVariable Long id,@Valid @RequestBody BatteryRequest request){
        return batteryService.updateBattery(id,request);
    }

    @DeleteMapping
    public String deleteAllBateries(){
        return batteryService.deleteAllBatteries();
    }

    @DeleteMapping("/{id}")
    public String deleteBattery(@PathVariable Long id){
        return batteryService.deleteBattery(id);
    }

    @PostMapping("/{id}/charge")
    public HistoryResponse chargeBattery(@PathVariable Long id, @Valid @RequestBody HistoryRequest request){
        return batteryService.chargeBattery(id,request);
    }

    @PostMapping("/{id}/discharge")
    public HistoryResponse dischargeBattery(@PathVariable Long id,@Valid @RequestBody HistoryRequest request){
        return batteryService.dischargeBattery(id,request);
    }

    @GetMapping("/{id}/percentage")
    public PercentageResponse getPercentage(@PathVariable Long id){
        return batteryService.getPercentage(id);
    }

    @GetMapping("/{id}/status")
    public BatteryStatusResponse getStatus(@PathVariable Long id){
        return batteryService.getStatus(id);
    }
}
