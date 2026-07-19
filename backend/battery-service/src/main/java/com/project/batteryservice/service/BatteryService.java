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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class BatteryService {
    private final BatteryRepository batteryRepository;
    private final BatteryEntityToResponse entityToResponse;
    private final BatteryRequestToEntity requestToEntity;
    private final HistoryEntityToResponse historyEntityToResponse;
    private final HistoryRequestToEntity historyRequestToEntity;
    private final BatteryHistoryRepository historyRepository;
    private static final Logger logger = LoggerFactory.getLogger(BatteryService.class);
    public BatteryService(BatteryRepository batteryRepository, BatteryEntityToResponse entityToResponse, BatteryRequestToEntity requestToEntity, HistoryEntityToResponse historyEntityToResponse, HistoryRequestToEntity historyRequestToEntity, BatteryHistoryRepository historyRepository) {
        this.batteryRepository = batteryRepository;
        this.entityToResponse = entityToResponse;
        this.requestToEntity = requestToEntity;
        this.historyEntityToResponse = historyEntityToResponse;
        this.historyRequestToEntity = historyRequestToEntity;
        this.historyRepository = historyRepository;
    }

    public BatteryResponse registerBattery(BatteryRequest request){
        logger.info("Registering new battery: {}", request.getBatteryName());
        Battery battery = requestToEntity.toEntity(request);
        batteryRepository.save(battery);
        logger.info("Battery registered successfully with ID: {}", battery.getId());
        return entityToResponse.toResponse(battery);
    }

    public List<BatteryResponse> getAllBatteries(){
        logger.info("Fetching all batteries");
        List<Battery> batteries = batteryRepository.findAll();
        logger.info("Retrieved {} batteries", batteries.size());
        return entityToResponse.toListResponse(batteries);
    }

    public BatteryResponse getBatteryById(Long id){
        logger.info("Fetching battery with ID: {}", id);
        Battery battery = batteryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Battery not found with ID: {}", id);
                    return new BatteryNotFoundException("The requested battery is not found/exists");
                });
        logger.info("Successfully retrieved battery with ID: {}", id);
        return entityToResponse.toResponse(battery);
    }

    public BatteryResponse updateBattery(Long id,BatteryRequest request){
        logger.info("Updating battery with ID: {}", id);
        Battery battery = batteryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Battery not found with ID: {}", id);
                    return new BatteryNotFoundException("The requested battery is not found/exists");
                });
        requestToEntity.updateEntity(request,battery);
        batteryRepository.save(battery);
        logger.info("Battery updated successfully with ID: {}", id);
        return entityToResponse.toResponse(battery);
    }

    public String deleteAllBatteries(){
        logger.info("Deleting all batteries");
        batteryRepository.deleteAll();
        logger.info("All batteries deleted successfully");
        return "All batteries are removed successfully";
    }
    public String deleteBattery(Long id){
        logger.info("Deleting battery with ID: {}", id);
        batteryRepository.deleteById(id);
        logger.info("Battery deleted successfully with ID: {}", id);
        return "Battery with id " + id + " is deleted successfully";
    }

    public HistoryResponse chargeBattery(Long id, HistoryRequest request){
        logger.info("Charging battery with ID: {}", id);
        Battery battery = batteryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Battery not found with ID: {}", id);
                    return new BatteryNotFoundException("The requested battery is not found/exists");
                });
        logger.info("Charging battery {} with {} units", id, request.getUnits());
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

        logger.info("Battery {} current charge: {} units",id,battery.getCurrentCharge());
        return historyEntityToResponse.toHistoryResponse(history);
    }

    public HistoryResponse dischargeBattery(Long id,HistoryRequest request){
        logger.info("Discharging battery with ID: {}", id);
        Battery battery = batteryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Battery not found with ID: {}", id);
                    return new BatteryNotFoundException("The requested battery is not found/exists");
                });
        logger.info("Discharging {} units from battery {}",request.getUnits(),id);
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
            logger.warn("Battery {} is below 20% ({}%)", id, percentage);
            response.setAlertMessage("LOW BATTERY");
        }
        logger.info("Battery {} remaining charge: {} units",id,battery.getCurrentCharge());
        return response;
    }

    public PercentageResponse getPercentage(Long id){
        logger.info("Calculating battery percentage for ID: {}", id);
        Battery battery = batteryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Battery not found with ID: {}", id);
                    return new BatteryNotFoundException("The requested battery is not found/exists");
                });
        PercentageResponse response = new PercentageResponse();
        response.setBatteryId(battery.getId());
        double percentage = (battery.getCurrentCharge() / battery.getCapacity()) * 100;
        percentage = Math.round(percentage * 100.0) / 100.0;
        if(percentage < 0 || percentage > 100){
            logger.error("Invalid battery percentage {} for battery {}", percentage, id);
            throw new InvalidBatteryPercentageException("The battery percentage must be within 0 - 100");
        }
        response.setPercentage(percentage);
        logger.info("Battery {} charge percentage: {}%", id, percentage);
        return response;
    }

    public BatteryStatusResponse getStatus(Long id){
        logger.info("Fetching battery status for ID: {}", id);
        Battery battery = batteryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Battery not found with ID: {}", id);
                    return new BatteryNotFoundException("The requested battery is not found/exists");
                });
        BatteryStatusResponse response = new BatteryStatusResponse();
        double percentage = (battery.getCurrentCharge() / battery.getCapacity()) * 100;
        response.setBatteryId(battery.getId());
        if(percentage <= 20){
            logger.warn("Battery {} is below 20% ({}%)", id, percentage);
            response.setStatus("LOW BATTERY");
        }
        else{
            response.setStatus("NORMAL");
        }
        logger.info("Battery {} status: {}", id, response.getStatus());
        return  response;
    }

}
