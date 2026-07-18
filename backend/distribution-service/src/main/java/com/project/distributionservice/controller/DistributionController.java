package com.project.distributionservice.controller;

import com.project.distributionservice.dto.DistributionRequest;
import com.project.distributionservice.dto.DistributionResponse;
import com.project.distributionservice.service.DistributionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/distribution")
public class DistributionController {
    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping("/balance")
    public DistributionResponse balanceEnergy(DistributionRequest request){
        return distributionService.balanceEnergy(request);
    }
}
