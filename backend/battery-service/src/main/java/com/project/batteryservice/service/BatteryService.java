package com.project.batteryservice.service;

import com.project.batteryservice.dto.*;
import com.project.batteryservice.entity.Battery;
import com.project.batteryservice.entity.BatteryHistory;
import com.project.batteryservice.exception.BatteryNotFoundException;
import com.project.batteryservice.exception.InvalidBatteryPercentageException;
import com.project.batteryservice.mapper.BatteryEntityToResponse;
import com.project.batteryservice.mapper.BatteryRequestToEntity;
import com.project.batteryservice.mapper.HistoryEntityToResponse;
import com.project.batteryservice.mapper.HistoryRequestToEntity;
import com.project.batteryservice.repository.BatteryHistoryRepository;
import com.project.batteryservice.repository.BatteryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BatteryService {
    private final BatteryRepository batteryRepository;
    private final BatteryEntityToResponse entityToResponse;
    private final BatteryRequestToEntity requestToEntity;
    private final HistoryEntityToResponse historyEntityToResponse;
    private final HistoryRequestToEntity historyRequestToEntity;
    private final BatteryHistoryRepository historyRepository;

    public BatteryService(BatteryRepository batteryRepository, BatteryEntityToResponse entityToResponse, BatteryRequestToEntity requestToEntity, HistoryEntityToResponse historyEntityToResponse, HistoryRequestToEntity historyRequestToEntity, BatteryHistoryRepository historyRepository) {
        this.batteryRepository = batteryRepository;
        this.entityToResponse = entityToResponse;
        this.requestToEntity = requestToEntity;
        this.historyEntityToResponse = historyEntityToResponse;
        this.historyRequestToEntity = historyRequestToEntity;
        this.historyRepository = historyRepository;
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

    public HistoryResponse chargeBattery(Long id, HistoryRequest request){
        Battery battery = batteryRepository.findById(id).orElseThrow( () -> new BatteryNotFoundException("The requested battery is not found/exists"));
        BatteryHistory history = historyRequestToEntity.toHistoryEntity(request);
        history.setBattery(battery);
        history.setOperation("CHARGE");

        double newCharge = battery.getCurrentCharge() + request.getUnits();
        if (newCharge > battery.getCapacity()) {
            newCharge = battery.getCapacity();
        }
        battery.setCurrentCharge(newCharge);
        batteryRepository.save(battery);
        historyRepository.save(history);

        return historyEntityToResponse.toHistoryResponse(history);
    }

    public HistoryResponse dischargeBattery(Long id,HistoryRequest request){
        Battery battery = batteryRepository.findById(id).orElseThrow( () -> new BatteryNotFoundException("The requested battery is not found/exists"));
        BatteryHistory history = historyRequestToEntity.toHistoryEntity(request);
        history.setBattery(battery);
        history.setOperation("DISCHARGE");

        double remainingCharge = battery.getCurrentCharge() - request.getUnits();
        if (remainingCharge < 0) {
            remainingCharge = 0;
        }
        double percentage = (remainingCharge / battery.getCapacity()) * 100;
        battery.setCurrentCharge(remainingCharge);
        batteryRepository.save(battery);
        historyRepository.save(history);

        HistoryResponse response = historyEntityToResponse.toHistoryResponse(history);
        if (percentage <= 20) {
            response.setAlertMessage("LOW BATTERY");
        }
        return response;
    }

    public PercentageResponse getPercentage(Long id){
        Battery battery = batteryRepository.findById(id).orElseThrow( () -> new BatteryNotFoundException("The requested battery is not found/exists"));
        PercentageResponse response = new PercentageResponse();
        response.setBatteryId(battery.getId());
        double percentage = (battery.getCurrentCharge() / battery.getCapacity()) * 100;
        percentage = Math.round(percentage * 100.0) / 100.0;
        if(percentage < 0 || percentage > 100){
            throw new InvalidBatteryPercentageException("The battery percentage must be within 0 - 100");
        }
        response.setPercentage(percentage);
        return response;
    }

    public BatteryStatusResponse getStatus(Long id){
        Battery battery = batteryRepository.findById(id).orElseThrow( () -> new BatteryNotFoundException("The requested battery is not found/exists"));
        BatteryStatusResponse response = new BatteryStatusResponse();
        double percentage = (battery.getCurrentCharge() / battery.getCapacity()) * 100;
        response.setBatteryId(battery.getId());
        if(percentage <= 20){
            response.setStatus("LOW BATTERY");
        }
        else{
            response.setStatus("NORMAL");
        }
        return  response;
    }

}
