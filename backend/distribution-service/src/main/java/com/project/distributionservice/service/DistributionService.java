package com.project.distributionservice.service;

import com.project.distributionservice.dto.*;
import com.project.distributionservice.entity.DistributionHistory;
import com.project.distributionservice.exception.DistributionException;
import com.project.distributionservice.mapper.DistributionEntityToResponse;
import com.project.distributionservice.mapper.DistributionRequestToEntity;
import com.project.distributionservice.repository.DistributionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DistributionService {
    private final DistributionRepository distributionRepository;
    private final RestTemplate restTemplate;
    private final DistributionRequestToEntity requestToEntity;
    private final DistributionEntityToResponse entityToResponse;
    public DistributionService(DistributionRepository distributionRepository, RestTemplate restTemplate, DistributionRequestToEntity requestToEntity, DistributionEntityToResponse entityToResponse) {
        this.distributionRepository = distributionRepository;
        this.restTemplate = restTemplate;
        this.requestToEntity = requestToEntity;
        this.entityToResponse = entityToResponse;
    }

    public DistributionResponse balanceEnergy(DistributionRequest request){
        ResponseEntity<SolarResponse[]> solarResponse =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/v1/solar-panels",
                        SolarResponse[].class);

        SolarResponse[] solarPanels = solarResponse.getBody();
        if (solarPanels == null) {
            throw new DistributionException("Unable to fetch solar panel data.");
        }
        double solarUnits =
                Arrays.stream(solarPanels)
                        .filter(s -> s.getStatus().equalsIgnoreCase("ACTIVE"))
                        .mapToDouble(SolarResponse::getCurrent_generation)
                        .sum();


        ResponseEntity<WindTurbineResponse[]> windTurbineResponse =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/v1/wind-turbines",
                        WindTurbineResponse[].class);

        WindTurbineResponse[] windTurbines = windTurbineResponse.getBody();
        if (windTurbines == null) {
            throw new DistributionException("Unable to fetch wind turrbine data.");
        }
        double windUnits =
                Arrays.stream(windTurbines)
                        .filter(s -> s.getStatus().equalsIgnoreCase("ACTIVE"))
                        .mapToDouble(WindTurbineResponse::getCurrentGeneration)
                        .sum();

        double renewable = solarUnits + windUnits;

        DistributionHistory history = requestToEntity.toDistribution(request);
        history.setRenewableUnits(renewable);

        if (renewable >= request.getRequiredUnits()) {

            ResponseEntity<BatteryResponse[]> batteryResponse =
                    restTemplate.getForEntity(
                            "http://localhost:8080/api/v1/batteries",
                            BatteryResponse[].class);

            BatteryResponse[] batteries = batteryResponse.getBody();

            double excess = renewable - request.getRequiredUnits();
            double stored = 0;

            if (batteries != null) {

                for (BatteryResponse battery : batteries) {

                    if (!battery.getStatus().equalsIgnoreCase("ACTIVE")) {
                        continue;
                    }

                    double available =
                            battery.getCapacity() - battery.getCurrentCharge();

                    if (available <= 0) {
                        continue;
                    }

                    double chargeUnits = Math.min(excess, available);

                    HistoryRequest charge = new HistoryRequest();
                    charge.setUnits(chargeUnits);

                    restTemplate.postForObject(
                            "http://localhost:8080/api/v1/batteries/"
                                    + battery.getId()
                                    + "/charge",
                            charge,
                            HistoryResponse.class
                    );

                    stored += chargeUnits;
                    excess -= chargeUnits;

                    if (excess <= 0) {
                        break;
                    }
                }
            }

            history.setBatteryUsed(0.0);
            history.setExcessUnitsStored(stored);

            if (excess > 0) {
                history.setStatus("PARTIALLY STORED");
            } else {
                history.setStatus("STORED");
            }

            distributionRepository.save(history);

            return entityToResponse.toResponse(history);
        }

        else {

            ResponseEntity<BatteryResponse[]> batteryResponse =
                    restTemplate.getForEntity(
                            "http://localhost:8080/api/v1/batteries",
                            BatteryResponse[].class);

            BatteryResponse[] batteries = batteryResponse.getBody();

            double requiredFromBattery = request.getRequiredUnits() - renewable;
            double batteryUsed = 0;

            if (batteries != null) {

                for (BatteryResponse battery : batteries) {

                    if (!battery.getStatus().equalsIgnoreCase("ACTIVE")) {
                        continue;
                    }

                    if (battery.getCurrentCharge() <= 0) {
                        continue;
                    }

                    double dischargeUnits = Math.min(requiredFromBattery,
                            battery.getCurrentCharge());

                    HistoryRequest discharge = new HistoryRequest();
                    discharge.setUnits(dischargeUnits);

                    restTemplate.postForObject(
                            "http://localhost:8080/api/v1/batteries/"
                                    + battery.getId()
                                    + "/discharge",
                            discharge,
                            HistoryResponse.class
                    );

                    batteryUsed += dischargeUnits;
                    requiredFromBattery -= dischargeUnits;

                    if (requiredFromBattery <= 0) {
                        break;
                    }
                }
            }

            history.setBatteryUsed(batteryUsed);
            history.setExcessUnitsStored(0.0);

            if (requiredFromBattery > 0) {
                history.setStatus("INSUFFICIENT BATTERY");
            } else {
                history.setStatus("BATTERY USED");
            }

            distributionRepository.save(history);

            return entityToResponse.toResponse(history);
        }
    }

    public List<DistributionResponse> getAllDistributions(){
        List<DistributionHistory> histories = distributionRepository.findAll();
        return entityToResponse.toListResponse(histories);
    }

    public DistributionResponse getDistributionById(Long id){
        DistributionHistory history = distributionRepository.findById(id).orElseThrow( () -> new DistributionException("The requested distribution not found"));
        return entityToResponse.toResponse(history);
    }
}
