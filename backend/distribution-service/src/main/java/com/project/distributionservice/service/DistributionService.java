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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class DistributionService {
    private static final Logger logger =
            LoggerFactory.getLogger(DistributionService.class);
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
        logger.info("Received distribution request for {} units",
                request.getRequiredUnits());
        logger.info("Fetching today's solar generation report");
        ResponseEntity<SolarResponse[]> solarResponse =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/v1/solar-panels",
                        SolarResponse[].class);
        SolarResponse[] solarPanels = solarResponse.getBody();
        if (solarPanels == null) {
            logger.error("Failed to fetch solar panel data from Solar Service");
            throw new DistributionException("Unable to fetch solar panel data.");
        }
        double solarUnits =
                Arrays.stream(solarPanels)
                        .filter(s -> s.getStatus().equalsIgnoreCase("ACTIVE"))
                        .mapToDouble(SolarResponse::getCurrent_generation)
                        .sum();


        logger.info("Solar generated {} units", solarUnits);

        logger.info("Fetching today's wind generation report");
        ResponseEntity<WindTurbineResponse[]> windTurbineResponse =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/v1/wind-turbines",
                        WindTurbineResponse[].class);

        WindTurbineResponse[] windTurbines = windTurbineResponse.getBody();
        if (windTurbines == null) {
            logger.error("Failed to fetch wind turbine data from wind Service");
            throw new DistributionException("Unable to fetch wind turrbine data.");
        }
        double windUnits =
                Arrays.stream(windTurbines)
                        .filter(s -> s.getStatus().equalsIgnoreCase("ACTIVE"))
                        .mapToDouble(WindTurbineResponse::getCurrentGeneration)
                        .sum();

        logger.info("Wind generated {} units", windUnits);
        double renewable = solarUnits + windUnits;

        logger.info("Total renewable energy available: {}", renewable);
        DistributionHistory history = requestToEntity.toDistribution(request);
        history.setRenewableUnits(renewable);

        if (renewable >= request.getRequiredUnits()) {
            logger.info("Fetching battery details");
            ResponseEntity<BatteryResponse[]> batteryResponse =
                    restTemplate.getForEntity(
                            "http://localhost:8080/api/v1/batteries",
                            BatteryResponse[].class);

            BatteryResponse[] batteries = batteryResponse.getBody();

            double excess = renewable - request.getRequiredUnits();
            double stored = 0;

            logger.info("Excess energy detected: {} units", excess);
            logger.info("Sending excess energy to Battery Service");
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
                    logger.info(
                            "Charging battery {} with {} units",
                            battery.getId(),
                            chargeUnits
                    );

                    stored += chargeUnits;
                    excess -= chargeUnits;

                    if (excess <= 0) {
                        break;
                    }

                }
                logger.info("Total energy stored in batteries: {} units", stored);

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
            logger.info("Fetching battery details");
            ResponseEntity<BatteryResponse[]> batteryResponse =
                    restTemplate.getForEntity(
                            "http://localhost:8080/api/v1/batteries",
                            BatteryResponse[].class);

            BatteryResponse[] batteries = batteryResponse.getBody();

            double requiredFromBattery = request.getRequiredUnits() - renewable;
            double batteryUsed = 0;

            logger.warn("Renewable energy is insufficient");
            logger.info("Requesting {} units from Battery Service", requiredFromBattery);
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

                    logger.info(
                            "Discharging {} units from battery {}",
                            dischargeUnits,
                            battery.getId()
                    );
                    batteryUsed += dischargeUnits;
                    requiredFromBattery -= dischargeUnits;

                    if (requiredFromBattery <= 0) {
                        break;
                    }
                }
                logger.info("Total battery energy used: {} units", batteryUsed);
            }

            history.setBatteryUsed(batteryUsed);
            history.setExcessUnitsStored(0.0);

            if (requiredFromBattery > 0) {
                logger.error(
                        "Battery energy is insufficient. Remaining required units: {}",
                        requiredFromBattery
                );
                history.setStatus("INSUFFICIENT BATTERY");
            } else {
                history.setStatus("BATTERY USED");
            }

            distributionRepository.save(history);

            logger.info(
                    "Distribution completed with status {}",
                    history.getStatus()
            );
            return entityToResponse.toResponse(history);
        }
    }

    public List<DistributionResponse> getAllDistributions(){
        logger.info("Fetching all distribution records");
        List<DistributionHistory> histories = distributionRepository.findAll();
        logger.info("Retrieved {} distribution records", histories.size());
        return entityToResponse.toListResponse(histories);
    }

    public DistributionResponse getDistributionById(Long id){
        logger.info("Fetching distribution details by using id : {}",id);
        DistributionHistory history = distributionRepository.findById(id).
                orElseThrow( () -> {
                    logger.error("Distribution record not found with ID: {}", id);
                    return new DistributionException("The requested distribution not found");
                });
        return entityToResponse.toResponse(history);
    }

    public DailyReportResponse getDailyReport(LocalDate date) {

        logger.info("Generating daily renewable energy report for {}", date);

        logger.info("Fetching solar generation report for {}", date);
        SolarReportResponse solar = restTemplate.getForObject(
                "http://localhost:8080/api/v1/solar-panels/reports?date=" + date,
                SolarReportResponse.class);

        logger.info("Fetching wind generation report for {}", date);
        WindReportResponse wind = restTemplate.getForObject(
                "http://localhost:8080/api/v1/wind-turbines/reports?date=" + date,
                WindReportResponse.class);

        DailyReportResponse response = new DailyReportResponse();

        response.setDate(date);
        response.setSolarGeneration(solar.getTotalGeneratedUnits());
        response.setWindGeneration(wind.getTotalGeneratedPower());

        response.setTotalRenewableGeneration(
                solar.getTotalGeneratedUnits()
                        + wind.getTotalGeneratedPower());

        logger.info(
                "Daily report generated successfully. Solar: {}, Wind: {}, Total: {}",
                response.getSolarGeneration(),
                response.getWindGeneration(),
                response.getTotalRenewableGeneration());

        return response;
    }
    public FaultReportResponse getFaults() {

        logger.info("Fetching renewable energy fault reports");

        ResponseEntity<SolarFaultResponse[]> solarResponse =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/v1/solar-panels/faults",
                        SolarFaultResponse[].class);

        ResponseEntity<WindFaultResponse[]> windResponse =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/v1/wind-turbines/faults",
                        WindFaultResponse[].class);

        List<SolarFaultResponse> solarFaults =
                Arrays.asList(solarResponse.getBody());

        List<WindFaultResponse> windFaults =
                Arrays.asList(windResponse.getBody());

        FaultReportResponse response = new FaultReportResponse();

        response.setSolarFaults(solarFaults);
        response.setWindFaults(windFaults);

        response.setTotalFaults(
                solarFaults.size() + windFaults.size());

        logger.info(
                "Retrieved {} solar faults and {} wind faults. Total faults: {}",
                solarFaults.size(),
                windFaults.size(),
                response.getTotalFaults());

        return response;
    }
}
