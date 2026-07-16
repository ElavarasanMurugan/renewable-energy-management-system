package com.project.batteryservice.service;

import com.project.batteryservice.dto.BatteryRequest;
import com.project.batteryservice.dto.BatteryResponse;
import com.project.batteryservice.entity.Battery;
import com.project.batteryservice.exception.BatteryNotFoundException;
import com.project.batteryservice.mapper.BatteryEntityToResponse;
import com.project.batteryservice.mapper.BatteryRequestToEntity;
import com.project.batteryservice.repository.BatteryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryService {
    private final BatteryRepository batteryRepository;
    private final BatteryEntityToResponse entityToResponse;
    private final BatteryRequestToEntity requestToEntity;

    public BatteryService(BatteryRepository batteryRepository, BatteryEntityToResponse entityToResponse, BatteryRequestToEntity requestToEntity) {
        this.batteryRepository = batteryRepository;
        this.entityToResponse = entityToResponse;
        this.requestToEntity = requestToEntity;
    }

    public BatteryResponse registerBattery(BatteryRequest request){
        Battery battery = requestToEntity.toEntity(request);
        batteryRepository.save(battery);
        return entityToResponse.toResponse(battery);
    }

    public List<BatteryResponse> getAllBatteries(){
        List<Battery> batteries = batteryRepository.findAll();
        return entityToResponse.toListResponse(batteries);
    }

    public BatteryResponse getBatteryById(Long id){
        Battery battery = batteryRepository.findById(id).orElseThrow( () -> new BatteryNotFoundException("The requested battery is not found/exists"));
        return entityToResponse.toResponse(battery);
    }

    public BatteryResponse updateBattery(Long id,BatteryRequest request){
        Battery battery = batteryRepository.findById(id).orElseThrow( () -> new BatteryNotFoundException("The requested battery is not found/exists"));
        requestToEntity.updateEntity(request,battery);
        batteryRepository.save(battery);
        return entityToResponse.toResponse(battery);
    }

    public String deleteAllBatteries(){
        batteryRepository.deleteAll();
        return "All batteries are removed successfully";
    }
    public String deleteBattery(Long id){
        batteryRepository.deleteById(id);
        return "Battery with id " + id + " is deleted successfully";
    }
}
