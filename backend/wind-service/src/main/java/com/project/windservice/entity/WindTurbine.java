package com.project.windservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="wind_turbine")
@Data
public class WindTurbine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceName;
    private String location;
    private Double capacity;
    private Double currentGeneration;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
