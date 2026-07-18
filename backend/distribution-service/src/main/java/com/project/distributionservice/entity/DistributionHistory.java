package com.project.distributionservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "distribution_history")
@Data
public class DistributionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double requiredUnits;
    private Double renewableUnits;
    private Double batteryUsed;
    private Double excessUnitsStored;
    private String status;

    @CreationTimestamp
    private LocalDateTime distributedAt;
}
