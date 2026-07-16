package com.project.windservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "wind_generation_history")
@Data
public class WindGenerationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "generated_power",nullable = false)
    private Double generatedPower;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @ManyToOne
    @JoinColumn(name="turbine_id",nullable = false)
    private WindTurbine windTurbine;
}
