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

    @Column(name = "required_units",nullable = false)
    private Double requiredUnits;
    @Column(name = "renewable_units",nullable = false)
    private Double renewableUnits;
    @Column(name = "battery_used",nullable = false)
    private Double batteryUsed;
    @Column(name = "excess_stored",nullable = false)
    private Double excessUnitsStored;
    @Column(name = "status",nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "distributed_at", updatable = false)
    private LocalDateTime distributedAt;
}
