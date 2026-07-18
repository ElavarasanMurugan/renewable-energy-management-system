package com.project.distributionservice.controller;

import com.project.distributionservice.dto.DistributionRequest;
import com.project.distributionservice.dto.DistributionResponse;
import com.project.distributionservice.service.DistributionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/distribution")
public class DistributionController {
    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping("/balance")
    public DistributionResponse balanceEnergy(@Valid @RequestBody DistributionRequest request){
        return distributionService.balanceEnergy(request);
    }

    @GetMapping
    public List<DistributionResponse> getAllDistributions(){
        return distributionService.getAllDistributions();
    }

    @GetMapping("/{id}")
    public DistributionResponse getDistributionById(@PathVariable Long id){
        return distributionService.getDistributionById(id);
    }
}
